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
import io.siddharth.myapplication.R;
import io.siddharth.myapplication.adapter.HomePagerAdapter;
import io.siddharth.myapplication.databinding.FragmentHomeBinding;
import io.siddharth.myapplication.ui.viewmodel.StudentViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private StudentViewModel studentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Scope StudentViewModel to Activity so sub-fragments share the same instance
        studentViewModel = new ViewModelProvider(requireActivity()).get(StudentViewModel.class);

        setupViewPager();
    }

    private void setupViewPager() {
        HomePagerAdapter adapter = new HomePagerAdapter(this);
        binding.medicalPager.setAdapter(adapter);

        // Link TabLayout with ViewPager2
        new TabLayoutMediator(binding.tabLayout, binding.medicalPager, (tab, position) -> {
            if (position == 0) tab.setText("Ongoing");
            else tab.setText("Scheduled");
        }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}