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
        View view = LayoutInflater.from(mContext).inflate(R.layout.cv_dosen, parent, false);
        return new PengujiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengujiViewHolder holder, int position) {
        Penguji penguji = mPengujiList.get(position);
        holder.textPenguji.setText(penguji.getNama());
        holder.textNIP.setText("NIP: "+penguji.getNip());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailPenguji.class);
                intent.putExtra("nama_penguji", penguji.getNama());
                intent.putExtra("nip_penguji", penguji.getNip());
                mContext.startActivity(intent);
            }
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
            textPenguji = itemView.findViewById(R.id.textnamadosen);
            textNIP = itemView.findViewById(R.id.textNIP);
        }
    }

    public static class Penguji {
        private String nama_penguji;
        private String nip_penguji;

        public Penguji(String nama, String nip) {
            this.nama_penguji = nama;
            this.nip_penguji = nip;
        }

        public String getNama() {
            return nama_penguji;
        }

        public String getNip() {
            return nip_penguji;
        }
    }
}
