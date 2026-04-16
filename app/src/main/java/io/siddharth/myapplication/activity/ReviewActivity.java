package io.siddharth.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import io.siddharth.myapplication.databinding.ActivityReviewBinding;
import io.siddharth.myapplication.ui.viewmodel.ReviewViewModel;

public class ReviewActivity extends AppCompatActivity {

    private ActivityReviewBinding binding;
    private ReviewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        // Retrieve the String ID passed from previous screen
        String studentId = getIntent().getStringExtra("STUDENT_ID");
        if (studentId != null) {
            viewModel.init(studentId);
        }

        setupToolbar();
        observeData();

        binding.btnFinalSubmit.setOnClickListener(v -> {
            viewModel.submitFinal();
            finish();
        });
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        binding.toolbar.toolbarTitle.setText("Review Assessment");
        binding.toolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void observeData() {
        // 'growth' resolves because it is now public in the ReviewViewModel
        viewModel.growth.observe(this, data -> {
            if (data != null) {
                binding.cardGrowth.tvHeightValue.setText(data.height + " cm");
                binding.cardGrowth.tvWeightValue.setText(data.weight + " kg");
            }
        });

        viewModel.physical.observe(this, data -> {
            if (data != null) {
                binding.cardPhysical.tvPhysicalSummary.setText("Hygiene: " + data.Hygiene_Hair);
            }
        });

        viewModel.eye.observe(this, data -> {
            if (data != null) {
                binding.cardEye.tvEyeSummary.setText("Right: " + data.optometryRight + " | Left: " + data.optometryLeft);
            }
        });

        viewModel.dental.observe(this, data -> {
            if (data != null) {
                binding.cardDental.tvDentalSummary.setText("Caries: " + data.Dental_Caries);
            }
        });

        viewModel.ent.observe(this, data -> {
            if (data != null) {
                binding.cardEnt.tvEntSummary.setText("Ear Wax: " + data.Right_Ear_Ear_Wax);
            }
        });
    }
}