package io.siddharth.myapplication.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.data.repository.LoginRepository;
import io.siddharth.myapplication.domain.model.LoginViewModel;
import io.siddharth.myapplication.domain.model.LoginViewModelFactory;
import io.siddharth.myapplication.fragments.LoadingDialog;

public class LoginActivity extends AppCompatActivity {
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

        // 1. Initialize the Repository
        LoginRepository repository = new LoginRepository();
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

    private void updateLoginButtonState() {
        String userId = userIdEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String assessment = assessmentEditText.getText().toString().trim();

        boolean isEnabled = !userId.isEmpty() && !password.isEmpty() && !assessment.isEmpty();
        loginButton.setEnabled(isEnabled);

        if (isEnabled) {
            loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_theme));
        } else {
            loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparentMore));
        }
    }

    private void observeViewModel() {
        viewModel.getLoginState().observe(this, state -> {
            // If state is null, stop here
            if (state == null) return;

            // React based on which Enum value was sent
            switch (state) {
                case LOADING:
                    showLoading(); // Show your DialogFragment
                    break;

                case SUCCESS:
                    hideLoading();
                    // Navigate to DashboardActivity
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
        // Extra safety check to prevent overlapping dialogs
        if (progressDialog == null || !progressDialog.isAdded()) {
            progressDialog = LoadingDialog.show(getSupportFragmentManager());
        }
    }

    // To hide it
    private void hideLoading() {
        if (progressDialog != null && progressDialog.isAdded()) {
            progressDialog.dismiss();
        }
    }
}