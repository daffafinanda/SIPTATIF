package com.example.siptatif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MahasiswaAdapter mAdapter;
    private List<MahasiswaAdapter.Mahasiswa> mMahasiswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_data);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            } else if (itemId == R.id.bottom_data) {
                return true;
            } else if (itemId == R.id.bottom_lecturer) {
                startActivity(new Intent(getApplicationContext(), LecturerActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            }
            return false;
        });

        // Initialize RecyclerView
        mRecyclerView = findViewById(R.id.list_mahasiswa);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize data mahasiswa
        mMahasiswaList = new ArrayList<>();
        mMahasiswaList.add(new MahasiswaAdapter.Mahasiswa("23498435", "Jane Doe", "987654321", "Machine Learning", "Menunggu..."));
        mMahasiswaList.add(new MahasiswaAdapter.Mahasiswa("456476", "Alice Smith", "456789123", "Data Analysis", "Diterima"));
        mMahasiswaList.add(new MahasiswaAdapter.Mahasiswa("76423534", "Bob Johnson", "789123456", "Artificial Intelligence", "Ditolak"));

        // Initialize adapter RecyclerView
        mAdapter = new MahasiswaAdapter(this, mMahasiswaList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.info) {
            Toast.makeText(this, "Info Akun", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.setting) {
            Toast.makeText(this, "Pengaturan", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}


