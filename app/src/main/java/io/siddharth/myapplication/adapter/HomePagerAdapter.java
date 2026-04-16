package io.siddharth.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import io.siddharth.myapplication.fragments.AssessmentListFragment;
import io.siddharth.myapplication.fragments.StudentListFragment;

public class HomePagerAdapter extends FragmentStateAdapter {

    public HomePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new AssessmentListFragment(); // Tab 0: Ongoing
        } else {
            return new StudentListFragment(); // Tab 1: Scheduled
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}