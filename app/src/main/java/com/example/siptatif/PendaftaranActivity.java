package com.example.siptatif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendaftaranActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PendaftaranAdapter adapter;
    private List<PendaftaranAdapter.Pendaftaran> pendaftaranList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

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
        recyclerView = findViewById(R.id.list_mahasiswa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!TextUtils.isEmpty(token)) {
            // Membuat instance ApiService menggunakan Retrofit
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

            // Membuat panggilan API dengan token
            Call<List<PendaftaranAdapter.Pendaftaran>> call = apiService.getPendaftaran("Bearer " + token);
            call.enqueue(new Callback<List<PendaftaranAdapter.Pendaftaran>>() {
                @Override
                public void onResponse(Call<List<PendaftaranAdapter.Pendaftaran>> call, Response<List<PendaftaranAdapter.Pendaftaran>> response) {
                    if (response.isSuccessful()) {
                        // Panggilan berhasil, lakukan pemrosesan data
                        pendaftaranList = response.body();
                        adapter = new PendaftaranAdapter(PendaftaranActivity.this, pendaftaranList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        // Panggilan gagal, tangani respons tidak berhasil
                        // Contoh: handle response code 401 (Unauthorized)
                        if (response.code() == 401) {
                            // Token tidak valid atau telah kedaluwarsa
                            // Lakukan tindakan sesuai, misalnya arahkan pengguna untuk masuk kembali
                        } else {
                            // Tangani kasus lain jika diperlukan
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<PendaftaranAdapter.Pendaftaran>> call, Throwable t) {
                    // Tangani kegagalan jaringan atau kesalahan lainnya
                }
            });
        } else {
            // Token tidak tersedia, arahkan pengguna untuk masuk kembali atau lakukan tindakan lain
            // Contoh: mungkin arahkan pengguna ke layar masuk
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            finish(); // Tutup PendaftaranActivity ketika BerkasActivity selesai
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
            SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();

            // Redirect ke LoginActivity
            Intent intent = new Intent(PendaftaranActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}