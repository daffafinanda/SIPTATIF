package com.example.siptatif;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BerkasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berkas);

        ViewPager2 viewPager = findViewById(R.id.viewPagerBerkas);
        TabLayout tabLayout = findViewById(R.id.tabLayoutBerkas);

        BerkasAdapter adapter = new BerkasAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Berkas Input");
                        break;
                    case 1:
                        tab.setText("Berkas Mahasiswa");
                        break;
                }
            }
        }).attach();
    }
}

