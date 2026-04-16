package io.siddharth.myapplication.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import io.siddharth.myapplication.databinding.ActivityGrowthBinding;
import io.siddharth.myapplication.ui.viewmodel.GrowthViewModel;

public class GrowthActivity extends AppCompatActivity {

    private ActivityGrowthBinding binding;
    private GrowthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGrowthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GrowthViewModel.class);

        setupToolbar();
        setupListeners();

        viewModel.bmiResult.observe(this, bmi -> binding.tvBmiResult.setText(bmi));
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.toolbarTitle.setText("Growth Assessment");
        binding.toolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupListeners() {
        TextWatcher bmiWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                viewModel.height.setValue(binding.etHeight.getText().toString());
                viewModel.weight.setValue(binding.etWeight.getText().toString());
                viewModel.calculateBmi();
            }
        };

        binding.etHeight.addTextChangedListener(bmiWatcher);
        binding.etWeight.addTextChangedListener(bmiWatcher);

        binding.btnSaveGrowth.setOnClickListener(v -> {
            // Logic to save the data
            finish();
        });
    }
}