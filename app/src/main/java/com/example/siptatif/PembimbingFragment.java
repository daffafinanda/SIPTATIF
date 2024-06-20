package com.example.siptatif;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        mRecyclerView = view.findViewById(R.id.list_dosen_pembimbing);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Ambil token dari SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Memeriksa apakah token tersedia
        if (!TextUtils.isEmpty(token)) {
            // Inisialisasi ApiService
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

            // Membuat panggilan API dengan token
            Call<List<PembimbingAdapter.Pembimbing>> call = apiService.getPembimbing("Bearer " + token);
            call.enqueue(new Callback<List<PembimbingAdapter.Pembimbing>>() {
                @Override
                public void onResponse(Call<List<PembimbingAdapter.Pembimbing>> call, Response<List<PembimbingAdapter.Pembimbing>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Set data ke adapter RecyclerView
                        mPembimbingList = response.body();
                        mAdapter = new PembimbingAdapter(getContext(), mPembimbingList);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        // Tangani kasus respons tidak berhasil
                        // Tampilkan pesan kepada pengguna
                        Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<PembimbingAdapter.Pembimbing>> call, Throwable t) {
                    // Tangani kegagalan panggilan API
                    // Tampilkan pesan kepada pengguna
                    Toast.makeText(getContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Token tidak tersedia, arahkan pengguna untuk masuk kembali atau lakukan tindakan lain
            // Contoh: mungkin arahkan pengguna ke layar masuk
            Toast.makeText(getContext(), "Token not available, please log in", Toast.LENGTH_SHORT).show();
            // Contoh: arahkan pengguna ke layar login
            // Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            // startActivity(loginIntent);
        }
    }
}
