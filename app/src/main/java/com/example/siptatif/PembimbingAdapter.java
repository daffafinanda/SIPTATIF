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
        View view = LayoutInflater.from(mContext).inflate(R.layout.cv_dosen_pembimbing_list, parent, false);
        return new PembimbingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PembimbingViewHolder holder, int position) {
        Pembimbing pembimbing = mPembimbingList.get(position);
        holder.textPembimbing.setText(pembimbing.getNama());
        holder.textNIP.setText(pembimbing.getNip());

        // Set OnClickListener for buttons
        holder.buttonDetailPembimbing.setOnClickListener(v -> {
            // Detail button logic
        });

        holder.buttonEditPembimbing.setOnClickListener(v -> {
            // Edit button logic
        });

        holder.buttonDeletePembimbing.setOnClickListener(v -> {
            // Delete button logic
        });
    }

    @Override
    public int getItemCount() {
        return mPembimbingList.size();
    }

    static class PembimbingViewHolder extends RecyclerView.ViewHolder {

        TextView textPembimbing, textNIP;
        Button buttonDetailPembimbing, buttonEditPembimbing, buttonDeletePembimbing;

        PembimbingViewHolder(@NonNull View itemView) {
            super(itemView);
            textPembimbing = itemView.findViewById(R.id.textPembimbing);
            textNIP = itemView.findViewById(R.id.textNIP);
            buttonDetailPembimbing = itemView.findViewById(R.id.buttonDetailPembimbing);
            buttonEditPembimbing = itemView.findViewById(R.id.buttonEditPembimbing);
            buttonDeletePembimbing = itemView.findViewById(R.id.buttonDeletePembimbing);
        }
    }

    public static class Pembimbing {
        private String nama;
        private String nip;

        public Pembimbing(String nama, String nip) {
            this.nama = nama;
            this.nip = nip;
        }

        public String getNama() {
            return nama;
        }

        public String getNip() {
            return nip;
        }
    }
}
