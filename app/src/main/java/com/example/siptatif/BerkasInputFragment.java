package com.example.siptatif;

import static com.example.siptatif.ApiClient.getRetrofitInstance;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerkasInputFragment extends Fragment {

    private Spinner spinnerPenguji1, spinnerPenguji2;
    private TableLayout tableLayout;
    private List<Dosen> dosenList;
    private HashMap<String, String> dosenMap;

    private String calonPenguji1;
    private String calonPenguji2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            calonPenguji1 = getArguments().getString("Penguji1");
            calonPenguji2 = getArguments().getString("Penguji2");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_berkas_input, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);

        spinnerPenguji1 = view.findViewById(R.id.berkas_penguji1);
        spinnerPenguji2 = view.findViewById(R.id.berkas_penguji2);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPenguji1.setAdapter(adapter);
        spinnerPenguji2.setAdapter(adapter);

        spinnerPenguji1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPenguji1 = parent.getItemAtPosition(position).toString();
                String nipPenguji1 = dosenMap.get(selectedPenguji1); // Get NIP based on selected dosen name
                String selectedPenguji2 = spinnerPenguji2.getSelectedItem().toString(); // Get selected penguji2
                String nipPenguji2 = dosenMap.get(selectedPenguji2); // Get NIP based on selected dosen name
                if (getActivity() instanceof OnPengujiSelectedListener) {
                    ((OnPengujiSelectedListener) getActivity()).onPengujiSelected(nipPenguji1, nipPenguji2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        spinnerPenguji2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPenguji2 = parent.getItemAtPosition(position).toString();
                String nipPenguji2 = dosenMap.get(selectedPenguji2); // Get NIP based on selected dosen name
                String selectedPenguji1 = spinnerPenguji1.getSelectedItem().toString(); // Get selected penguji1
                String nipPenguji1 = dosenMap.get(selectedPenguji1); // Get NIP based on selected dosen name
                if (getActivity() instanceof OnPengujiSelectedListener) {
                    ((OnPengujiSelectedListener) getActivity()).onPengujiSelected(nipPenguji1, nipPenguji2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        // Ambil token dari SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);

        // Memeriksa apakah token tersedia
        if (!TextUtils.isEmpty(token)) {
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            fetchDosenData(apiService, token);
        } else {
            // Token tidak tersedia, arahkan pengguna untuk masuk kembali atau lakukan tindakan lain
            Toast.makeText(getActivity(), "Token not available, please log in", Toast.LENGTH_SHORT).show();
            // Contoh: arahkan pengguna ke layar login
            // Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
            // startActivity(loginIntent);
        }

        return view;
    }

    private void fetchDosenData(ApiService apiService, String token) {
        apiService.getDosen("Bearer " + token).enqueue(new Callback<List<Dosen>>() {
            @Override
            public void onResponse(Call<List<Dosen>> call, Response<List<Dosen>> response) {
                if (response.isSuccessful()) {
                    dosenList = response.body();
                    populateSpinner();
                    populateTable(); // Perbarui tabel setelah mengambil data dosen
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dosen>> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void populateSpinner() {
        if (getActivity() != null && dosenList != null) {
            dosenMap = new HashMap<>();
            List<String> namaList = new ArrayList<>();

            // Tambahkan item kosong di awal daftar
            namaList.add(" "); // Atau bisa juga "Select" atau teks sesuai kebutuhan

            for (Dosen dosen : dosenList) {
                namaList.add(dosen.getNama());
                dosenMap.put(dosen.getNama(), dosen.getNIP());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, namaList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerPenguji1.setAdapter(adapter);
            spinnerPenguji2.setAdapter(adapter);

            // Set default value for spinner
            if (!TextUtils.isEmpty(calonPenguji1)) {
                int spinnerPosition1 = adapter.getPosition(calonPenguji1);
                if (spinnerPosition1 >= 0) {
                    spinnerPenguji1.setSelection(spinnerPosition1);
                }
            }
            if (!TextUtils.isEmpty(calonPenguji2)) {
                int spinnerPosition2 = adapter.getPosition(calonPenguji2);
                if (spinnerPosition2 >= 0) {
                    spinnerPenguji2.setSelection(spinnerPosition2);
                }
            }
        }
    }

    private void populateTable() {
        if (tableLayout != null) {
            if (dosenList != null) {
                for (Dosen dosen : dosenList) {
                    TableRow row = new TableRow(getActivity());
                    row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                    TextView namaTextView = new TextView(getActivity());
                    namaTextView.setText(dosen.getNama());
                    namaTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 5));
                    namaTextView.setPadding(8, 8, 8, 8);
                    namaTextView.setGravity(Gravity.CENTER);
                    namaTextView.setTextColor(Color.BLACK);
                    row.addView(namaTextView);

                    TextView nipTextView = new TextView(getActivity());
                    nipTextView.setText(dosen.getNIP());
                    nipTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 3));
                    nipTextView.setPadding(8, 8, 8, 8);
                    nipTextView.setGravity(Gravity.CENTER);
                    nipTextView.setTextColor(Color.BLACK);
                    row.addView(nipTextView);

                    tableLayout.addView(row);
                }
            }
        }
    }

    public interface OnPengujiSelectedListener {
        void onPengujiSelected(String nipPenguji1, String nipPenguji2);
    }

}