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
import io.siddharth.myapplication.adapter.DoctorListAdapter;
import io.siddharth.myapplication.databinding.FragmentManagerBinding;
import io.siddharth.myapplication.ui.viewmodel.ManagerViewModel;

public class ManagerFragment extends Fragment {

    private FragmentManagerBinding binding;
    private ManagerViewModel viewModel;
    private DoctorListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentManagerBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ManagerViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        observeDoctors();
        setupClickListeners();
    }

    private void setupRecyclerView() {
        adapter = new DoctorListAdapter(doctor -> {
            // Handle doctor click
        });

        // IMPORTANT: In your XML, change <ListView id="@+id/listview">
        // to <androidx.recyclerview.widget.RecyclerView>
        // For now, if it's still a ListView, this code will need an ArrayAdapter.
    }

    private void observeDoctors() {
        viewModel.getDoctors().observe(getViewLifecycleOwner(), list -> {
            if (list != null) adapter.submitList(list);
        });
        viewModel.loadDoctors();
    }

    private void setupClickListeners() {
        binding.addCoordinator.setOnClickListener(v -> viewModel.addCoordinator());
        binding.trainingChildLayout.setOnClickListener(v -> viewModel.training());
        binding.helpSupportLayout.setOnClickListener(v -> viewModel.showHelpSupportDialog());
        binding.addButtonLayout.setOnClickListener(v -> viewModel.addMoreDoctor());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}