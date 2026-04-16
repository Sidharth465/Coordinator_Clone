package io.siddharth.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.databinding.ActivityStudentHubBinding;
import io.siddharth.myapplication.domain.model.StudentModel;

public class StudentHubActivity extends AppCompatActivity {
    private ActivityStudentHubBinding binding;
    private StudentModel student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
    binding = ActivityStudentHubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        student = (StudentModel)  getIntent().getSerializableExtra("StudentModel");
        setupToolbar();
        setupClickListeners();

    }
    private void setupToolbar(){
        setSupportActionBar(binding.toolbar.toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        }
        binding.toolbar.toolbarTitle.setText(student != null ? student.getName() : "Student Details");

    }


    private void setupClickListeners() {
        binding.cardProfile.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentRegActivity.class);
            intent.putExtra("StudentModel", student);
            startActivity(intent);
        });

        binding.cardAssessment.setOnClickListener(v -> {
            Intent intent = new Intent(this, AssessmentActivity.class);
            intent.putExtra("StudentModel", student);
            startActivity(intent);
        });
    }
}