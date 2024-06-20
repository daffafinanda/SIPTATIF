package com.example.siptatif;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class BerkasMahasiswaFragment extends Fragment {

    private TextView tanggal, nama, nim, email, judul, kategoriTA, calonpembimbing1, calonpembimbing2;
    private Spinner statusSpinner;
    private String[] statusOptions = {"", "Diterima", "Ditolak"}; // Include an empty option at the beginning

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_berkas_mahasiswa, container, false);

        tanggal = view.findViewById(R.id.berkas_tanggal);
        nama = view.findViewById(R.id.berkas_nama);
        nim = view.findViewById(R.id.berkas_nim);
        email = view.findViewById(R.id.berkas_email);
        judul = view.findViewById(R.id.berkas_judul);
        kategoriTA = view.findViewById(R.id.berkas_kategori);
        calonpembimbing1 = view.findViewById(R.id.berkas_pembimbing1);
        calonpembimbing2 = view.findViewById(R.id.berkas_pembimbing2);
        statusSpinner = view.findViewById(R.id.berkas_status_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, statusOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                if (getActivity() instanceof OnStatusSelectedListener) {
                    ((OnStatusSelectedListener) getActivity()).onStatusSelected(selectedStatus);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            tanggal.setText(bundle.getString("tanggal"));
            nama.setText(bundle.getString("nama"));
            nim.setText(bundle.getString("nim"));
            email.setText(bundle.getString("email"));
            judul.setText(bundle.getString("judul"));
            kategoriTA.setText(bundle.getString("kategoriTA"));
            calonpembimbing1.setText(bundle.getString("calonPembimbing1"));
            calonpembimbing2.setText(bundle.getString("calonPembimbing2"));

            // Set default selected item in the spinner based on the received status value
            String status = bundle.getString("status");
            if (status != null) {
                switch (status.toLowerCase()) { // Convert status to lowercase for comparison
                    case "diterima":
                        statusSpinner.setSelection(1); // Index 1 corresponds to "Diterima"
                        break;
                    case "ditolak":
                        statusSpinner.setSelection(2); // Index 2 corresponds to "Ditolak"
                        break;
                    default:
                        statusSpinner.setSelection(0); // Index 0 corresponds to the empty option
                        break;
                }
            }
        }

        return view;
    }

    public interface OnStatusSelectedListener {
        void onStatusSelected(String status);
    }


}
