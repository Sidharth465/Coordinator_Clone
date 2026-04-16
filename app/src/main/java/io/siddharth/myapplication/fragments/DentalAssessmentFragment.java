package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.siddharth.myapplication.databinding.FragmentDentalAssessmentBinding;
import io.siddharth.myapplication.domain.model.DentalAssessmentModel;
import io.siddharth.myapplication.ui.viewmodel.AssessmentViewModel;

public class DentalAssessmentFragment extends Fragment {

    private FragmentDentalAssessmentBinding binding;
    private AssessmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDentalAssessmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);

        observeViewModel();
        setupListeners();
    }

    private void observeViewModel() {
        viewModel.getDentalData().observe(getViewLifecycleOwner(), data -> {
            binding.cbCaries.setChecked("true".equals(data.Dental_Caries));
            binding.cbTartar.setChecked("true".equals(data.Tartar_Plaque));

            // Avoid infinite loop by checking if text is actually different
            if (!binding.etDentalRemarks.getText().toString().equals(data.Dental_Remarks)) {
                binding.etDentalRemarks.setText(data.Dental_Remarks);
            }
        });
    }

    private void setupListeners() {
        binding.cbCaries.setOnCheckedChangeListener((v, isChecked) ->
                updateModel(model -> model.Dental_Caries = isChecked ? "true" : ""));

        binding.cbTartar.setOnCheckedChangeListener((v, isChecked) ->
                updateModel(model -> model.Tartar_Plaque = isChecked ? "true" : ""));

        binding.etDentalRemarks.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateModel(model -> model.Dental_Remarks = s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void updateModel(ModelUpdater updater) {
        DentalAssessmentModel current = viewModel.getDentalData().getValue();
        if (current != null) {
            updater.update(current);
            viewModel.updateDentalData(current);
        }
    }

    private interface ModelUpdater { void update(DentalAssessmentModel model); }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}