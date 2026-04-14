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
            return new AssessmentListFragment();
        }
        return new StudentListFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
