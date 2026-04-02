package io.siddharth.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.data.repository.LoginRepository;
import io.siddharth.myapplication.domain.model.LoginViewModel;
import io.siddharth.myapplication.domain.model.LoginViewModelFactory;
import io.siddharth.myapplication.fragments.LoadingDialog;

public class LoginActivity extends AppCompatActivity   {
    private LoadingDialog progressDialog;
    private LoginViewModel viewModel;
    private EditText userIdEditText;
    private EditText passwordEditText;
    private EditText assessmentEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        userIdEditText = findViewById(R.id.user_id);
        passwordEditText = findViewById(R.id.password);
        assessmentEditText = findViewById(R.id.assessment);
        loginButton = findViewById(R.id.btnLogin);

        // 1. Initialize the Repository with context
        LoginRepository repository = new LoginRepository(this);
        // 2 Initialize the factory with that repository
        LoginViewModelFactory factory = new LoginViewModelFactory(repository);

        // 3. Initialize ViewModel using the Factory
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);
        // 4. Start Observing the Login State
        observeViewModel();

        // Add TextWatchers to monitor input
        TextWatcher loginTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateLoginButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        userIdEditText.addTextChangedListener(loginTextWatcher);
        passwordEditText.addTextChangedListener(loginTextWatcher);
        assessmentEditText.addTextChangedListener(loginTextWatcher);

        // Initial state check
        updateLoginButtonState();

        // 5. Set up your Button Click
        loginButton.setOnClickListener(v -> {
            // Sync UI to ViewModel
            viewModel.setUserId(userIdEditText.getText().toString());
            viewModel.setPassword(passwordEditText.getText().toString());
            viewModel.setAssessmentId(assessmentEditText.getText().toString());

            // Trigger login
            viewModel.onLogin();
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                int[] outLocation = new int[2];
                v.getLocationOnScreen(outLocation);
                float x = event.getRawX() + v.getLeft() - outLocation[0];
                float y = event.getRawY() + v.getTop() - outLocation[1];
                if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void updateLoginButtonState() {
        String userId = userIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String assessment = assessmentEditText.getText().toString().trim();

        boolean isEnabled = !userId.isEmpty() && !password.isEmpty() && !assessment.isEmpty();
        loginButton.setEnabled(isEnabled);

        // Using ViewCompat.setBackgroundTintList to preserve the rounded corners shape while changing color
        if (isEnabled) {
            ViewCompat.setBackgroundTintList(loginButton, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorActiveLoginButton)));
        } else {
            ViewCompat.setBackgroundTintList(loginButton, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorTransparentMore)));
        }
    }

    private void observeViewModel() {
        viewModel.getLoginState().observe(this, state -> {
            if (state == null) return;

            switch (state) {
                case LOADING:
                    showLoading();
                    break;

                case SUCCESS:
                    hideLoading();
                    // Navigate to DashboardActivity
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("is_new_user", false);
                    startActivity(intent);
                    finish(); // Close Login so user can't go back
                    break;

                case ERROR_EMPTY_FIELDS:
                    hideLoading();
                    Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                    break;

                case ERROR_NETWORK:
                    hideLoading();
                    Toast.makeText(this, "Connection failed. Try again.", Toast.LENGTH_SHORT).show();
                    break;

                case IDLE:
                    hideLoading();
                    break;
            }
        });

    }

    private void showLoading() {
        if (progressDialog == null || !progressDialog.isAdded()) {
            progressDialog = LoadingDialog.show(getSupportFragmentManager());
        }
    }

    private void hideLoading() {
        if (progressDialog != null && progressDialog.isAdded()) {
            progressDialog.dismiss();
        }
    }


}