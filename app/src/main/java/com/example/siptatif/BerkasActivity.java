package com.example.siptatif;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerkasActivity extends AppCompatActivity implements BerkasMahasiswaFragment.OnStatusSelectedListener, BerkasInputFragment.OnPengujiSelectedListener  {

    private ViewPager2 viewPager;

    private Button buttonKembali;
    private TabLayout tabLayout;

    private String statusSelected;
    private String penguji1Selected;
    private String penguji2Selected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berkas);



        viewPager = findViewById(R.id.viewPagerBerkas);
        tabLayout = findViewById(R.id.tabLayoutBerkas);


        Button simpanButton = findViewById(R.id.buttonSimpanBerkas);
        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                finish();
            }
        });
        buttonKembali = findViewById(R.id.buttonKembali);
        buttonKembali.setOnClickListener(v -> finish()); // Menutup activity saat tombol "Kembali" ditekan


        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Mendapatkan data nim dari Intent
        String nim = getIntent().getStringExtra("nim");

        // Panggil method untuk mengambil data pendaftaran berdasarkan nim
        if (token != null && nim != null) {
            setupViewPager(nim, token);
        } else {
            Toast.makeText(this, "Token atau NIM tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupViewPager(String nim, String token) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<BerkasActivity.Pendaftaran> call = apiService.getPendaftaranNIM("Bearer " + token, nim);

        call.enqueue(new Callback<BerkasActivity.Pendaftaran>() {
            @Override
            public void onResponse(Call<BerkasActivity.Pendaftaran> call, Response<BerkasActivity.Pendaftaran> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BerkasActivity.Pendaftaran pendaftaran = response.body();

                    // Buat dua Bundle untuk mengirim data ke dua fragment berbeda
                    Bundle bundleMahasiswa = new Bundle();
                    bundleMahasiswa.putString("tanggal", pendaftaran.getTanggal());
                    bundleMahasiswa.putString("nama", pendaftaran.getNama());
                    bundleMahasiswa.putString("nim", pendaftaran.getNim());
                    bundleMahasiswa.putString("email", pendaftaran.getEmail());
                    bundleMahasiswa.putString("judul", pendaftaran.getJudul());
                    bundleMahasiswa.putString("calonPembimbing1", pendaftaran.getCalonPembimbing1());
                    bundleMahasiswa.putString("calonPembimbing2", pendaftaran.getCalonPembimbing2());
                    bundleMahasiswa.putString("status",pendaftaran.getStatus());
                    bundleMahasiswa.putString("kategoriTA", pendaftaran.getKategoriTA());

                    Bundle bundlePenguji = new Bundle();
                    bundlePenguji.putString("Penguji1", pendaftaran.getPenguji1());
                    bundlePenguji.putString("Penguji2", pendaftaran.getPenguji2());

                    BerkasAdapter adapter = new BerkasAdapter(BerkasActivity.this, bundleMahasiswa, bundlePenguji);
                    viewPager.setAdapter(adapter);

                    // Setup TabLayoutMediator setelah adapter diatur
                    new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            switch (position) {
                                case 0:
                                    tab.setText("Berkas Mahasiswa");
                                    break;
                                case 1:
                                    tab.setText("Input Dosen Penguji");
                                    break;
                            }
                        }
                    }).attach();
                } else {
                    Toast.makeText(BerkasActivity.this, "Gagal mengambil data pendaftaran", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BerkasActivity.Pendaftaran> call, Throwable t) {
                Toast.makeText(BerkasActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onStatusSelected(String status) {
        statusSelected = status;
    }

    public void onPengujiSelected(String penguji1, String penguji2) {
        penguji1Selected = penguji1;
        penguji2Selected = penguji2;
    }

    private void updateData() {
        SharedPreferences sharedPreferences = getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        String nim = getIntent().getStringExtra("nim");

        Log.d("BerkasActivity", "Status selected: " + statusSelected);
        Log.d("BerkasActivity", "Penguji 1 selected: " + penguji1Selected);
        Log.d("BerkasActivity", "Penguji 2 selected: " + penguji2Selected);

        if (token != null && nim != null) {
            // Mendapatkan instance Retrofit dari ApiClient
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

            // Update status jika statusSelected tidak null
            if (statusSelected != null) {
                StatusUpdateRequest statusRequest = new StatusUpdateRequest(statusSelected);
                Call<Void> callStatus = apiService.updateStatus("Bearer " + token, nim, statusRequest);
                callStatus.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("BerkasActivity", "Status update response code: " + response.code());
                        if (response.isSuccessful()) {
                            Toast.makeText(BerkasActivity.this, "Status berhasil diupdate", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BerkasActivity.this, "Gagal mengupdate status", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BerkasActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            // Update penguji jika penguji1Selected dan penguji2Selected tidak null
            if (penguji1Selected != null && penguji2Selected != null) {
                DosenPengujiUpdateRequest pengujiRequest = new DosenPengujiUpdateRequest(penguji1Selected, penguji2Selected);
                Call<Void> callPenguji = apiService.updateDosenPenguji("Bearer " + token, nim, pengujiRequest);
                callPenguji.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("BerkasActivity", "Penguji update response code: " + response.code());
                        if (response.isSuccessful()) {
                            Toast.makeText(BerkasActivity.this, "Data dosen penguji berhasil diupdate", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BerkasActivity.this, "Gagal mengupdate data dosen penguji", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(BerkasActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "Token atau NIM tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }


    public class StatusUpdateRequest {
        private String status;

        public StatusUpdateRequest(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public class DosenPengujiUpdateRequest {
        private String nip_penguji1;
        private String nip_penguji2;

        public DosenPengujiUpdateRequest(String nip_penguji1, String nip_penguji2) {
            this.nip_penguji1 = nip_penguji1;
            this.nip_penguji2 = nip_penguji2;
        }

        public String getPenguji1() {
            return nip_penguji1;
        }

        public void setPenguji1(String penguji1) {
            this.nip_penguji1 = penguji1;
        }

        public String getPenguji2() {
            return nip_penguji2;
        }

        public void setPenguji2(String penguji2) {
            this.nip_penguji2 = penguji2;
        }
    }


    public class Pendaftaran {
        private String tanggal;
        private String nama;
        private String nim;
        private String email;
        private String judul;
        private String calonPembimbing1;
        private String calonPembimbing2;
        private String Penguji1;
        private String Penguji2;
        private String status;
        private String kategoriTA;

        // Getter dan Setter untuk tanggal
        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        // Getter dan Setter untuk nama
        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        // Getter dan Setter untuk nim
        public String getNim() {
            return nim;
        }

        public void setNim(String nim) {
            this.nim = nim;
        }

        // Getter dan Setter untuk email
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        // Getter dan Setter untuk judul
        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        // Getter dan Setter untuk calonPembimbing1
        public String getCalonPembimbing1() {
            return calonPembimbing1;
        }

        public void setCalonPembimbing1(String calonPembimbing1) {
            this.calonPembimbing1 = calonPembimbing1;
        }

        // Getter dan Setter untuk calonPembimbing2
        public String getCalonPembimbing2() {
            return calonPembimbing2;
        }

        public void setCalonPembimbing2(String calonPembimbing2) {
            this.calonPembimbing2 = calonPembimbing2;
        }

        // Getter dan Setter untuk Penguji1
        public String getPenguji1() {
            return Penguji1;
        }

        public void setPenguji1(String Penguji1) {
            this.Penguji1 = Penguji1;
        }

        // Getter dan Setter untuk Penguji2
        public String getPenguji2() {
            return Penguji2;
        }

        public void setPenguji2(String Penguji2) {
            this.Penguji2 = Penguji2;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        // Getter dan Setter untuk kategoriTA
        public String getKategoriTA() {
            return kategoriTA;
        }

        public void setKategoriTA(String kategoriTA) {
            this.kategoriTA = kategoriTA;
        }
    }
}
