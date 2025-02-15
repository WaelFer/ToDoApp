package com.example.todo.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.todo.fragments.CheckedTasksFragment;
import com.example.todo.fragments.UncheckedTasksFragment;

public class TasksPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    public TasksPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UncheckedTasksFragment();
            case 1:
                return new CheckedTasksFragment();
            default:
                return new UncheckedTasksFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
