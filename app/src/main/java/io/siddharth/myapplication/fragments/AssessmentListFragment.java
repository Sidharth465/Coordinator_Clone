package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.siddharth.myapplication.R;
import io.siddharth.myapplication.adapter.AssessmentListAdapter;
import io.siddharth.myapplication.databinding.FragmentAssessmentListBinding;
import io.siddharth.myapplication.domain.model.AssessmentListModel;
import io.siddharth.myapplication.ui.viewmodel.StudentViewModel;
import io.siddharth.myapplication.util.Constants;

public class AssessmentListFragment extends Fragment {

    private FragmentAssessmentListBinding binding;
    private AssessmentListAdapter assessmentListAdapter;
    private StudentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssessmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Setup Adapter
        assessmentListAdapter = new AssessmentListAdapter(model -> {
            // Navigate to assessment activity
        });

        binding.assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.assessmentRecyclerView.setAdapter(assessmentListAdapter);

        // 3. Initialize Shared ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);

        // 4. Setup Search and Clear Icon
        binding.searchText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        binding.searchIcon.setOnClickListener(v -> {
            binding.searchText.setText("");
            viewModel.setSearchQuery("");
        });

        // 5. Observe Ongoing Students (This handles searching automatically)
        viewModel.getOngoingStudents().observe(getViewLifecycleOwner(), students -> {
            if (students != null) {
                List<AssessmentListModel> list = new ArrayList<>();
                for (io.siddharth.myapplication.domain.model.StudentModel student : students) {
                    AssessmentListModel model = new AssessmentListModel();
                    model.studentModel = student;
                    list.add(model);
                }
                assessmentListAdapter.submitList(list);
                
                // Update Count
                binding.countStudents.setText(String.valueOf(list.size()));
                
                // Show/Hide Empty View if it exists in layout
                if (binding.getRoot().findViewById(R.id.emptyView) != null) {
                    binding.getRoot().findViewById(R.id.emptyView).setVisibility(list.isEmpty() ? View.VISIBLE : View.GONE);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}