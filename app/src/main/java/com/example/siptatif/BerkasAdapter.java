package com.example.siptatif;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BerkasAdapter extends FragmentStateAdapter {

    public BerkasAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BerkasInputFragment();
            case 1:
                return new BerkasMahasiswaFragment();
            default:
                return new BerkasInputFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

