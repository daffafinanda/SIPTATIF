package com.example.siptatif;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class BerkasAdapter extends FragmentStateAdapter {

    private Bundle bundleMahasiswa;
    private Bundle bundlePenguji;

    public BerkasAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundleMahasiswa, Bundle bundlePenguji) {
        super(fragmentActivity);
        this.bundleMahasiswa = bundleMahasiswa;
        this.bundlePenguji = bundlePenguji;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                BerkasMahasiswaFragment berkasMahasiswaFragment = new BerkasMahasiswaFragment();
                berkasMahasiswaFragment.setArguments(bundleMahasiswa);
                return berkasMahasiswaFragment;
            case 1:
                BerkasInputFragment berkasInputFragment = new BerkasInputFragment();
                berkasInputFragment.setArguments(bundlePenguji);
                return berkasInputFragment;
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
