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

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PembimbingAdapter extends RecyclerView.Adapter<PembimbingAdapter.PembimbingViewHolder> {

    private Context mContext;
    private List<Pembimbing> mPembimbingList;

    public PembimbingAdapter(Context context, List<Pembimbing> pembimbingList) {
        mContext = context;
        mPembimbingList = pembimbingList;
    }

    @NonNull
    @Override
    public PembimbingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cv_dosen, parent, false);
        return new PembimbingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PembimbingViewHolder holder, int position) {
        Pembimbing pembimbing = mPembimbingList.get(position);
        holder.textPembimbing.setText(pembimbing.getNama());
        holder.textNIP.setText("NIP: "+ pembimbing.getNip());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailPembimbing.class);
                intent.putExtra("nama_pembimbing", pembimbing.getNama());
                intent.putExtra("nip_pembimbing", pembimbing.getNip());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPembimbingList.size();
    }

    static class PembimbingViewHolder extends RecyclerView.ViewHolder {

        TextView textPembimbing, textNIP;

        PembimbingViewHolder(@NonNull View itemView) {
            super(itemView);
            textPembimbing = itemView.findViewById(R.id.textnamadosen);
            textNIP = itemView.findViewById(R.id.textNIP);
        }
    }

    public static class Pembimbing {

        private String nama_pembimbing;
        private String nip_pembimbing;
        private String jenis_kelamin;

        public Pembimbing(String nama, String nip, String jeniskelamin) {
            this.nama_pembimbing = nama;
            this.nip_pembimbing = nip;
            this.jenis_kelamin = jeniskelamin;
        }

        public String getNama() {
            return nama_pembimbing;
        }

        public String getNip() {
            return nip_pembimbing;
        }
        public String getJK() {
            return jenis_kelamin;
        }
    }
}
