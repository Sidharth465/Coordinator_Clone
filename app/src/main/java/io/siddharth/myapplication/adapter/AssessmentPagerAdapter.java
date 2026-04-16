package io.siddharth.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import io.siddharth.myapplication.fragments.PhysicalAssessmentFragment;
// Import other fragments here

public class AssessmentPagerAdapter extends FragmentStateAdapter {

    public AssessmentPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new PhysicalAssessmentFragment();
            // case 1: return new EyeAssessmentFragment();
            // case 2: return new DentalAssessmentFragment();
            // case 3: return new ENTAssessmentFragment();
            default: return new PhysicalAssessmentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Physical, Eye, Dental, ENT
    }
}