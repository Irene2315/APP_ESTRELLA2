package com.example.front_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.front_android.Modelos.Incidencia;


public class IncidenciaFragment extends Fragment {

    private Incidencia incidenciaSelect;
    private TextView carreteraIncidencia;
    private TextView inicioIncidencia;
    private TextView causaIncidencia;
    private TextView tipoIncidencia;
    private TextView nivelIncidencia;
    private TextView regionIncidencia;
    private TextView provinciaIncidencia;
    private TextView ciudadIncidencia;


    public IncidenciaFragment() {
        // Required empty public constructor
    }



    public static IncidenciaFragment newInstance(Incidencia incidencia) {
        IncidenciaFragment fragment = new IncidenciaFragment();
        Bundle args = new Bundle();
        args.putSerializable("incidencia", incidencia);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_incidencia, container, false);

        carreteraIncidencia = view.findViewById(R.id.nombre_carretera);
        inicioIncidencia = view.findViewById(R.id.inicio_incidencia);
        causaIncidencia = view.findViewById(R.id.causa_incidencia);
        tipoIncidencia = view.findViewById(R.id.tipo_incidencia);
        nivelIncidencia = view.findViewById(R.id.nivel_incidencia);
        regionIncidencia = view.findViewById(R.id.region_incidencia);
        provinciaIncidencia = view.findViewById(R.id.provincia_incidencia);
        ciudadIncidencia = view.findViewById(R.id.ciudad_incidencia);

        incidenciaSelect = (Incidencia) getArguments().getSerializable("incidencia");

        Log.d("incidenciaFragment", "Incidencia seleccionada: " + incidenciaSelect.toString());


        if (incidenciaSelect.getCarretera() != null) {
            carreteraIncidencia.setText(incidenciaSelect.getCarretera());
        } else {
            carreteraIncidencia.setText("Carretera no disponible");
        }

        if(incidenciaSelect.getFechaInicio() !=null){
            inicioIncidencia.setText(incidenciaSelect.getFechaInicio());
        } else{
            inicioIncidencia.setText("Inicio incidencia no disponible");
        }

        if(incidenciaSelect.getCausa() != null){
            causaIncidencia.setText(incidenciaSelect.getCausa());
        } else{
            causaIncidencia.setText("Causa incidencia no disponible");
        }

        if(incidenciaSelect.getTipoIncidencia() !=null && incidenciaSelect.getTipoIncidencia().getNombre() !=null){
            tipoIncidencia.setText(incidenciaSelect.getTipoIncidencia().getNombre());
        } else{
            tipoIncidencia.setText("Tipo de incidencia no disponible");
        }
        if (incidenciaSelect.getNivelIncidencia() != null) {
            nivelIncidencia.setText(incidenciaSelect.getNivelIncidencia());
        } else {
            nivelIncidencia.setText("Nivel de incidencia no disponible");
        }

        if (incidenciaSelect.getRegion() != null && incidenciaSelect.getRegion().getNombreEs() != null) {
            regionIncidencia.setText(incidenciaSelect.getRegion().getNombreEs());
        } else {
            regionIncidencia.setText("Región no disponible");
        }

        if (incidenciaSelect.getProvincia() != null && incidenciaSelect.getProvincia().getNombre() != null) {
            provinciaIncidencia.setText(incidenciaSelect.getProvincia().getNombre());
        } else {
            provinciaIncidencia.setText("Provincia no disponible");
        }

        if (incidenciaSelect.getCiudad() != null && incidenciaSelect.getCiudad().getNombre() != null) {
            ciudadIncidencia.setText(incidenciaSelect.getCiudad().getNombre());
        } else {
            ciudadIncidencia.setText("Ciudad no disponible");
        }




        return view;
    }
}