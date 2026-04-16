package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import io.siddharth.myapplication.databinding.FragmentPhysicalAssessmentBinding;
import io.siddharth.myapplication.domain.model.PhysicalAssessmentModel;
import io.siddharth.myapplication.ui.viewmodel.AssessmentViewModel;


public class PhysicalAssessmentFragment extends Fragment {

    private FragmentPhysicalAssessmentBinding binding;
    private AssessmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhysicalAssessmentBinding.inflate(inflater, container, false);
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
        viewModel.getPhysicalData().observe(getViewLifecycleOwner(), data -> {
            binding.cbHandsNails.setChecked("true".equals(data.Hygiene_hands_and_nails));
            binding.cbHair.setChecked("true".equals(data.Hygiene_Hair));
            binding.cbPallor.setChecked("true".equals(data.General_signs_Pallor));
            binding.cbCyanosis.setChecked("true".equals(data.General_signs_Cynosis));
            binding.cbAnemia.setChecked("true".equals(data.General_signs_Anemia));
            binding.cbClubbing.setChecked("true".equals(data.General_signs_Clubbing));
            binding.cbEdema.setChecked("true".equals(data.General_signs_Edema));
        });
    }

    private void setupListeners() {
        binding.cbHandsNails.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.Hygiene_hands_and_nails = checked ? "true" : ""));

        binding.cbHair.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.Hygiene_Hair = checked ? "true" : ""));

        binding.cbPallor.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.General_signs_Pallor = checked ? "true" : ""));

        binding.cbCyanosis.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.General_signs_Cynosis = checked ? "true" : ""));

        binding.cbAnemia.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.General_signs_Anemia = checked ? "true" : ""));

        binding.cbClubbing.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.General_signs_Clubbing = checked ? "true" : ""));

        binding.cbEdema.setOnCheckedChangeListener((v, checked) ->
                updateData(d -> d.General_signs_Edema = checked ? "true" : ""));
    }

    private void updateData(DataUpdater updater) {
        PhysicalAssessmentModel current = viewModel.getPhysicalData().getValue();
        if (current != null) {
            updater.update(current);
            viewModel.getPhysicalData().setValue(current);
        }
    }

    interface DataUpdater {
        void update(PhysicalAssessmentModel model);
    }
}