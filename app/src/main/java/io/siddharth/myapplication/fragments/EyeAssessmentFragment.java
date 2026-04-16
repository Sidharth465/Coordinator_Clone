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
import io.siddharth.myapplication.databinding.FragmentEyeAssessmentBinding;
import io.siddharth.myapplication.domain.model.EyeAssessmentModel;
import io.siddharth.myapplication.ui.viewmodel.AssessmentViewModel;


public class EyeAssessmentFragment extends Fragment {
    private FragmentEyeAssessmentBinding binding;
    private AssessmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEyeAssessmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);

        viewModel.getEyeData().observe(getViewLifecycleOwner(), data -> {
            if (!binding.etVisionRight.getText().toString().equals(data.optometryRight)) {
                binding.etVisionRight.setText(data.optometryRight);
            }
            if (!binding.etVisionLeft.getText().toString().equals(data.optometryLeft)) {
                binding.etVisionLeft.setText(data.optometryLeft);
            }
        });

        binding.etVisionRight.addTextChangedListener(new SimpleTextWatcher(text ->
                updateData(d -> d.optometryRight = text)));
        binding.etVisionLeft.addTextChangedListener(new SimpleTextWatcher(text ->
                updateData(d -> d.optometryLeft = text)));
    }

    private void updateData(DataUpdater updater) {
        EyeAssessmentModel current = viewModel.getEyeData().getValue();
        if (current != null) {
            updater.update(current);
            viewModel.updateEyeData(current);
        }
    }

    interface DataUpdater { void update(EyeAssessmentModel model); }
    interface TextConsumer { void consume(String text); }

    private class SimpleTextWatcher implements TextWatcher {
        private final TextConsumer consumer;
        public SimpleTextWatcher(TextConsumer consumer) { this.consumer = consumer; }
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) { consumer.consume(s.toString()); }
        @Override public void afterTextChanged(Editable s) {}
    }
}