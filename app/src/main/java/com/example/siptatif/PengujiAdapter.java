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

public class PengujiAdapter extends RecyclerView.Adapter<PengujiAdapter.PengujiViewHolder> {

    private Context mContext;
    private List<Penguji> mPengujiList;

    public PengujiAdapter(Context context, List<Penguji> pengujiList) {
        mContext = context;
        mPengujiList = pengujiList;
    }

    @NonNull
    @Override
    public PengujiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cv_dosen_penguji_list, parent, false);
        return new PengujiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengujiViewHolder holder, int position) {
        Penguji penguji = mPengujiList.get(position);
        holder.textPenguji.setText(penguji.getNama());
        holder.textNIP.setText(penguji.getNip());

        holder.buttonDetailPenguji.setOnClickListener(v -> {
            // Detail button logic
        });

        holder.buttonEditPenguji.setOnClickListener(v -> {
            // Edit button logic
        });

        holder.buttonDeletePenguji.setOnClickListener(v -> {
            // Delete button logic
        });
    }

    @Override
    public int getItemCount() {
        return mPengujiList.size();
    }

    static class PengujiViewHolder extends RecyclerView.ViewHolder {

        TextView textPenguji, textNIP;
        Button buttonDetailPenguji, buttonEditPenguji, buttonDeletePenguji;

        PengujiViewHolder(@NonNull View itemView) {
            super(itemView);
            textPenguji = itemView.findViewById(R.id.textPenguji);
            textNIP = itemView.findViewById(R.id.textNIP);
            buttonDetailPenguji = itemView.findViewById(R.id.buttonDetailPenguji);
            buttonEditPenguji = itemView.findViewById(R.id.buttonEditPenguji);
            buttonDeletePenguji = itemView.findViewById(R.id.buttonDeletePenguji);
        }
    }

    public static class Penguji {
        private String nama;
        private String nip;

        public Penguji(String nama, String nip) {
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
