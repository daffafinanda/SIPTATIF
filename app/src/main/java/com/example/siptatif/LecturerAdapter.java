package com.example.siptatif;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LecturerAdapter extends FragmentStateAdapter {

    public LecturerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DosenFragment();
            case 1:
                return new PembimbingFragment();
            case 2:
                return new PengujiFragment();
            default:
                return new DosenFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
