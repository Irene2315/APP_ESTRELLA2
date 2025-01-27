package com.example.front_android;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.front_android.Modelos.Camara;


public class CamaraFragment extends Fragment {


    private Camara camaraSelect;
    private TextView nombreCamara;
    private TextView regionCamara;
    private ImageView imagenCamara;

    public CamaraFragment() {
        // Required empty public constructor
    }


    public static CamaraFragment newInstance(Camara camara) {
        CamaraFragment fragment = new CamaraFragment();
        Bundle args = new Bundle();
        args.putSerializable("camara", camara);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camara, container, false);

        nombreCamara = view.findViewById(R.id.nombre_camara);
        regionCamara = view.findViewById(R.id.region_camara);
        imagenCamara = view.findViewById(R.id.imagen_camara);

        camaraSelect = (Camara) getArguments().getSerializable("camara");

        Log.d("CamaraFragment", "Cámara seleccionada: " + camaraSelect.toString());


        if (camaraSelect.getNombre() != null) {
            nombreCamara.setText(camaraSelect.getNombre());
        } else {
            nombreCamara.setText("Nombre no disponible");
        }

        if (camaraSelect.getRegion() != null && camaraSelect.getRegion().getNombreEs() != null) {
            regionCamara.setText(camaraSelect.getRegion().getNombreEs());
        } else {
            regionCamara.setText("Región no disponible");
        }


        if (camaraSelect.getUrlImagen() != null && !camaraSelect.getUrlImagen().isEmpty()) {
            Glide.with(getContext())
                    .load(camaraSelect.getUrlImagen())
                    .placeholder(R.drawable.defecto)
                    .error(R.drawable.imagenerror)
                    .into(imagenCamara);
        } else {
            Glide.with(getContext())
                    .load(R.drawable.defecto)
                    .into(imagenCamara);
        }


        return view;
    }
}
