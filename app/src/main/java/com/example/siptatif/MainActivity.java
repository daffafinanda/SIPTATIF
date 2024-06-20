package com.example.siptatif;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mengecek token menggunakan SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // Jika user belum login, redirect ke LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Selesai MainActivity jika user belum login
        } else {
            // Jika user sudah login, lanjutkan dengan MainActivity
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
            bottomNavigationView.setSelectedItemId(R.id.bottom_home);

            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    // Sudah berada di halaman utama, tidak perlu melakukan apa-apa atau melakukan refresh data
                    return true;
                } else if (itemId == R.id.bottom_data) {
                    startActivity(new Intent(getApplicationContext(), PendaftaranActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    // Tidak perlu memanggil finish() di sini kecuali jika ingin menutup MainActivity
                    return true;
                } else if (itemId == R.id.bottom_lecturer) {
                    startActivity(new Intent(getApplicationContext(), LecturerActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    // Tidak perlu memanggil finish() di sini kecuali jika ingin menutup MainActivity
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profil) {
            Toast.makeText(this, "Profil", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.logout) {
            // Menghapus token dari SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Redirect ke LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
