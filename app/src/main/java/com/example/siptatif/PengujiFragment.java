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

public class PengujiFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PengujiAdapter mAdapter;
    private List<PengujiAdapter.Penguji> mPengujiList;

    public PengujiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penguji, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi RecyclerView
        mRecyclerView = view.findViewById(R.id.list_dosen_penguji);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inisialisasi data penguji
        mPengujiList = new ArrayList<>();
        mPengujiList.add(new PengujiAdapter.Penguji("John Doe", "123456789"));
        mPengujiList.add(new PengujiAdapter.Penguji("Alice Smith", "987654321"));

        // Inisialisasi adapter
        mAdapter = new PengujiAdapter(getContext(), mPengujiList);

        // Set adapter ke RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }
}

