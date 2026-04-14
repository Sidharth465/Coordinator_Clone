package io.siddharth.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayoutMediator;

import android.text.Editable;
import android.text.TextWatcher;
import io.siddharth.myapplication.R;
import io.siddharth.myapplication.adapter.HomePagerAdapter;
import io.siddharth.myapplication.databinding.FragmentHomeBinding;
import io.siddharth.myapplication.ui.viewmodel.StudentViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StudentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
        setupSearch();
    }

    private void setupSearch() {
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupViewPager() {
        HomePagerAdapter adapter = new HomePagerAdapter(this);
        binding.medicalPager.setAdapter(adapter);

        // Modern way to link TabLayout and ViewPager2
        new TabLayoutMediator(binding.tabLayout, binding.medicalPager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.tab_ongoing);
            } else {
                tab.setText(R.string.tab_scheduled);
            }
        }).attach();
        
        // Default to second tab as per your old code
        binding.medicalPager.setCurrentItem(1, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Important to avoid memory leaks
    }

    public void updateList() {
        // We will implement this using the Shared ViewModel later
    }
}
