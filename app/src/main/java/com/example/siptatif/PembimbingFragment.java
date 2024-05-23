package com.example.siptatif;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PembimbingFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PembimbingAdapter mAdapter;
    private List<PembimbingAdapter.Pembimbing> mPembimbingList;

    public PembimbingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pembimbing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi RecyclerView
        mRecyclerView = view.findViewById(R.id.list_dosen_pembimbing);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi data pembimbing
        mPembimbingList = new ArrayList<>();
        mPembimbingList.add(new PembimbingAdapter.Pembimbing("Dr. John Doe", "123456"));
        mPembimbingList.add(new PembimbingAdapter.Pembimbing("Dr. Alice Smith", "789012"));
        mPembimbingList.add(new PembimbingAdapter.Pembimbing("Prof. Bob Johnson", "345678"));
        mPembimbingList.add(new PembimbingAdapter.Pembimbing("Dr. Alice Smith", "789012"));
        mPembimbingList.add(new PembimbingAdapter.Pembimbing("Prof. Bob Johnson", "345678"));

        // Inisialisasi adapter RecyclerView
        mAdapter = new PembimbingAdapter(getContext(), mPembimbingList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
