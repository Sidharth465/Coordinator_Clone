package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

// 1. Updated import to match your layout filename
import io.siddharth.myapplication.databinding.FragmentCheckListDialogBinding;
import io.siddharth.myapplication.ui.viewmodel.CheckListViewModel;

public class CheckListDialogFragment extends DialogFragment {

    private FragmentCheckListDialogBinding binding; // 2. Updated class name
    private CheckListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 3. Updated to use the correct inflate method
        binding = FragmentCheckListDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CheckListViewModel.class);

        // Connect UI to ViewModel
        binding.mspCheckBox.setOnCheckedChangeListener((v, isChecked) -> viewModel.setMspCheck(isChecked));
        binding.ispCheckBox.setOnCheckedChangeListener((v, isChecked) -> viewModel.setIspCheck(isChecked));
        binding.studentCheckBox.setOnCheckedChangeListener((v, isChecked) -> viewModel.setStudentListCheck(isChecked));
        binding.tabChargedCheckBox.setOnCheckedChangeListener((v, isChecked) -> viewModel.setBatteryCheck(isChecked));

        // 4. Updated ID to binding.commentLayout to match android:id="@+id/comment_layout"
        if (binding.commentLayout.getEditText() != null) {
            binding.commentLayout.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    viewModel.setComment(s != null ? s.toString() : "");
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        // Observe LiveData to enable/disable button
        viewModel.isComplete.observe(getViewLifecycleOwner(), isComplete -> {
            binding.done.setEnabled(isComplete);
        });

        binding.done.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}