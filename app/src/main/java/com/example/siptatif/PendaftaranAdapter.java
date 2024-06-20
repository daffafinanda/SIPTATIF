package com.example.siptatif;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PendaftaranAdapter extends RecyclerView.Adapter<PendaftaranAdapter.MahasiswaViewHolder> {

    private final Context mContext;
    private final List<Pendaftaran> mMahasiswaList;

    public PendaftaranAdapter(Context context, List<Pendaftaran> pendaftaranList) {
        this.mContext = context;
        this.mMahasiswaList = pendaftaranList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cv_mahasiswa_list, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        Pendaftaran pendaftaran = mMahasiswaList.get(position);
        holder.tanggal.setText("Tanggal : " + pendaftaran.getTanggal());
        holder.textNama.setText("Nama: " + pendaftaran.getNama());
        holder.textNIM.setText("NIM: " + pendaftaran.getNim());
        holder.textJudulSkripsi.setText("Judul: " + pendaftaran.getJudul());
        holder.textStatus.setText("Status: " + pendaftaran.getStatus());

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BerkasActivity.class);
                intent.putExtra("nim", pendaftaran.getNim());
                ((PendaftaranActivity) mContext).startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMahasiswaList.size();
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        public TextView tanggal, textNama, textNIM, textJudulSkripsi, textStatus;
        public Button buttonEdit, buttonDelete;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            textNama = itemView.findViewById(R.id.textNama);
            textNIM = itemView.findViewById(R.id.textNIM);
            textJudulSkripsi = itemView.findViewById(R.id.textJudul);
            textStatus = itemView.findViewById(R.id.textStatus);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public static class Pendaftaran{
        private String tanggal;
        private String nama;
        private String nim;
        private String email;
        private String judul;
        private String calonPembimbing1;
        private String calonPembimbing2;
        private String berkas;
        private String status;
        private String catatan;

        public Pendaftaran(String tanggal, String nama, String nim, String email, String judul, String calonPembimbing1, String calonPembimbing2, String berkas, String status, String catatan, String calonpenguji1, String calonpenguji2) {
            this.tanggal = tanggal;
            this.nama = nama;
            this.nim = nim;
            this.email = email;
            this.judul = judul;
            this.calonPembimbing1 = calonPembimbing1;
            this.calonPembimbing2 = calonPembimbing2;
            this.berkas = berkas;
            this.status = status;
            this.catatan = catatan;
        }

        public String getTanggal() { return tanggal;}
        public String getNama() {
            return nama;
        }
        public String getNim() {
            return nim;
        }
        public String getEmail(){ return email; }
        public String getJudul() {
            return judul;
        }
        public String getCalonpembimbing1(){ return calonPembimbing1; }
        public String getCalonpembimbing2(){ return calonPembimbing2; }
        public String getBerkas(){ return berkas; }
        public String getStatus() {
            return status;
        }
        public String getCatatan(){ return catatan; }


    }
}