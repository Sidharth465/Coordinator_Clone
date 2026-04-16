package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.siddharth.myapplication.databinding.FragmentEntAssessmentBinding;
import io.siddharth.myapplication.domain.model.EntAssessmentModel;
import io.siddharth.myapplication.ui.viewmodel.AssessmentViewModel;

public class EntAssessmentFragment extends Fragment {

    private FragmentEntAssessmentBinding binding;
    private AssessmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEntAssessmentBinding.inflate(inflater, container, false);
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
        viewModel.getEntData().observe(getViewLifecycleOwner(), data -> {
            binding.cbEarWaxRight.setChecked("true".equals(data.Right_Ear_Ear_Wax));
            binding.cbEarWaxLeft.setChecked("true".equals(data.Left_Ear_Ear_Wax));
            binding.cbHearingLoss.setChecked("true".equals(data.Hearing_Loss));

            if (!binding.etEntRemarks.getText().toString().equals(data.Ent_Remarks)) {
                binding.etEntRemarks.setText(data.Ent_Remarks);
            }
        });
    }

    private void setupListeners() {
        binding.cbEarWaxRight.setOnCheckedChangeListener((v, isChecked) ->
                updateModel(model -> model.Right_Ear_Ear_Wax = isChecked ? "true" : ""));

        binding.cbEarWaxLeft.setOnCheckedChangeListener((v, isChecked) ->
                updateModel(model -> model.Left_Ear_Ear_Wax = isChecked ? "true" : ""));

        binding.cbHearingLoss.setOnCheckedChangeListener((v, isChecked) ->
                updateModel(model -> model.Hearing_Loss = isChecked ? "true" : ""));

        binding.etEntRemarks.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateModel(model -> model.Ent_Remarks = s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void updateModel(ModelUpdater updater) {
        EntAssessmentModel current = viewModel.getEntData().getValue();
        if (current != null) {
            updater.update(current);
            viewModel.updateEntData(current);
        }
    }

    private interface ModelUpdater { void update(EntAssessmentModel model); }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}