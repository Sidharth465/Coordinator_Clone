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

import io.siddharth.myapplication.adapter.StudentAdapter;
import io.siddharth.myapplication.databinding.FragmentAssessmentListBinding;
import io.siddharth.myapplication.ui.viewmodel.StudentViewModel;

public class AssessmentListFragment extends Fragment {

    private FragmentAssessmentListBinding binding;
    private StudentAdapter adapter;
    private StudentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAssessmentListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        observeStudents();
    }

    private void setupRecyclerView() {
        adapter = new StudentAdapter();
        binding.assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.assessmentRecyclerView.setAdapter(adapter);
    }

    private void observeStudents() {
        // Observe ongoing students (Status Ongoing)
        viewModel.getOngoingStudents().observe(getViewLifecycleOwner(), students -> {
            if (students != null) {
                adapter.submitList(students);
                binding.emptyView.setVisibility(students.isEmpty() ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
