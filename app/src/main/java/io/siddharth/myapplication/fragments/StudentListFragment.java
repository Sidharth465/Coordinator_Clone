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
import io.siddharth.myapplication.databinding.FragmentStudentListBinding;
import io.siddharth.myapplication.ui.viewmodel.StudentViewModel;

public class StudentListFragment extends Fragment {

    private FragmentStudentListBinding binding;
    private StudentAdapter adapter;
    private StudentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);
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
        binding.studentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.studentRecyclerView.setAdapter(adapter);
    }

    private void observeStudents() {
        // Observe scheduled students (Status Pending)
        viewModel.getScheduledStudents().observe(getViewLifecycleOwner(), students -> {
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
