package io.siddharth.myapplication.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.tabs.TabLayoutMediator;
import io.siddharth.myapplication.adapter.AssessmentPagerAdapter;
import io.siddharth.myapplication.databinding.ActivityAssessmentBinding;
import io.siddharth.myapplication.domain.model.StudentModel;
import io.siddharth.myapplication.ui.viewmodel.AssessmentViewModel;

public class AssessmentActivity extends AppCompatActivity {

    private ActivityAssessmentBinding binding;
    private AssessmentViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssessmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);
        StudentModel student = (StudentModel) getIntent().getSerializableExtra("StudentModel");
        viewModel.init(student);

        setupToolbar(student.getName());
        setupViewPager();
    }

    private void setupToolbar(String studentName) {
        setSupportActionBar(binding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        binding.toolbar.toolbarTitle.setText(studentName + " - Assessment");
        binding.toolbar.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupViewPager() {
        AssessmentPagerAdapter adapter = new AssessmentPagerAdapter(this);
        binding.assessmentPager.setAdapter(adapter);

        String[] tabs = {"Physical", "Eye", "Dental", "ENT"};
        new TabLayoutMediator(binding.assessmentTabs, binding.assessmentPager,
                (tab, position) -> tab.setText(tabs[position])
        ).attach();
    }
}