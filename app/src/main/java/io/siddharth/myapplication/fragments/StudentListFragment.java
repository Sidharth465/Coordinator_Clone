package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.siddharth.myapplication.adapter.StudentAdapter;
import io.siddharth.myapplication.databinding.FragmentStudentListBinding;
import io.siddharth.myapplication.ui.viewmodel.StudentViewModel;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;
import io.siddharth.myapplication.util.Constants;

public class StudentListFragment extends Fragment {

    private FragmentStudentListBinding binding;
    private StudentAdapter adapter;
    private StudentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStudentListBinding.inflate(inflater, container, false);
        // Use requireActivity() if you want to share data with other fragments in Dashboard
        viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupSearch();
        setupFilters();
        observeStudents();
    }

    private void setupFilters() {
        // 1. Class Spinner
        viewModel.getClasses().observe(getViewLifecycleOwner(), classes -> {
            List<String> classList = new ArrayList<>();
            classList.add("All");
            if (classes != null) classList.addAll(classes);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, classList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinner1.setAdapter(adapter);
        });

        // 2. Section Spinner
        binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedClass = parent.getItemAtPosition(position).toString();
                viewModel.getSections(selectedClass).observe(getViewLifecycleOwner(), sections -> {
                    List<String> sectionList = new ArrayList<>();
                    sectionList.add("All");
                    if (sections != null) sectionList.addAll(sections);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sectionList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinner2.setAdapter(adapter);
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 3. Status Spinner (Specific for Scheduled Tab)
        List<String> statusStrings = new ArrayList<>();
        statusStrings.add("All");
        statusStrings.add("Pending");
        statusStrings.add("Not Started");
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, statusStrings);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner3.setAdapter(statusAdapter);

        // 4. Go Button (Apply Filters)
        binding.applyFilters.setOnClickListener(v -> {
            String selClass = binding.spinner1.getSelectedItem() != null ? binding.spinner1.getSelectedItem().toString() : "All";
            String selSection = binding.spinner2.getSelectedItem() != null ? binding.spinner2.getSelectedItem().toString() : "All";
            String selStatusStr = binding.spinner3.getSelectedItem() != null ? binding.spinner3.getSelectedItem().toString() : "All";

            int status = -1; // Default for "All"
            if (selStatusStr.equals("Pending")) status = Constants.ASSESSMENT_STATUS_PENDING;
            else if (selStatusStr.equals("Not Started")) status = Constants.ASSESSMENT_STATUS_NOT_STARTED;

            viewModel.setSelectedClass(selClass);
            viewModel.setSelectedSection(selSection);
            viewModel.setSelectedStatus(status);
        });
    }

    private void setupRecyclerView() {
        adapter = new StudentAdapter();
        binding.studentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.studentRecyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
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

        binding.addButtonLayout.setOnClickListener(v -> {
            // Logic for registration
        });
    }

    private void observeStudents() {
        viewModel.getScheduledStudents().observe(getViewLifecycleOwner(), students -> {
            if (students != null) {
                adapter.submitList(students);
                binding.countStudents.setText(String.valueOf(students.size()));

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