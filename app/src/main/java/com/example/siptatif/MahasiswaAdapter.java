package com.example.siptatif;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {

    private final Context mContext;
    private final List<Mahasiswa> mMahasiswaList;

    public MahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        mContext = context;
        mMahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cv_mahasiswa_list, parent, false);
        return new MahasiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        Mahasiswa mahasiswa = mMahasiswaList.get(position);
        holder.textNoPendaftaran.setText("No : " + mahasiswa.getNopendaftaran());
        holder.textNama.setText("Nama: " + mahasiswa.getNama());
        holder.textNIM.setText("NIM: " + mahasiswa.getNim());
        holder.textJudulSkripsi.setText("Judul: " + mahasiswa.getJudul());
        holder.textStatus.setText("Status: " + mahasiswa.getStatus());
    }

    @Override
    public int getItemCount() {
        return mMahasiswaList.size();
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {
        public TextView textNoPendaftaran, textNama, textNIM, textJudulSkripsi, textStatus;
        public Button buttonEdit, buttonDelete;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            textNoPendaftaran = itemView.findViewById(R.id.textNoPendaftaran);
            textNama = itemView.findViewById(R.id.textNama);
            textNIM = itemView.findViewById(R.id.textNIM);
            textJudulSkripsi = itemView.findViewById(R.id.textJudul);
            textStatus = itemView.findViewById(R.id.textStatus);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }

    public static class Mahasiswa {
        private String nopendaftaran;
        private String nama;
        private String nim;
        private String email;
        private String judul;
        private String calondosenpembimbing1;
        private String calondosenpembimbing2;
        private String berkas;
        private String status;
        private String catatan;

        public Mahasiswa(String tanggal, String nama, String nim, String judul, String status) {
            this.nopendaftaran = tanggal;
            this.nama = nama;
            this.nim = nim;
            this.email = email;
            this.judul = judul;
            this.calondosenpembimbing1 = calondosenpembimbing1;
            this.calondosenpembimbing2 = calondosenpembimbing2;
            this.berkas = berkas;
            this.status = status;
            this.catatan = catatan;
        }

        public String getNopendaftaran() { return nopendaftaran;}
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
        public String getCalondosenpembimbing1(){ return calondosenpembimbing1; }
        public String getCalondosenpembimbing2(){ return calondosenpembimbing2; }
        public String getBerkas(){ return berkas; }
        public String getStatus() {
            return status;
        }
        public String getCatatan(){ return catatan; }
    }
}
