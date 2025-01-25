package com.example.front_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.front_android.Adaptadores.AdaptadorListaIncidencias;
import com.example.front_android.Adaptadores.WindowAdapterUniversal;
import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.PETICIONES_API.PeticionesIncidencias;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class IncidenciasFragment extends Fragment {


    private static ListView listaIncidencias;
    private static ArrayList<Incidencia> miListaIncidencias = new ArrayList<>();
    private static AdaptadorListaIncidencias adaptadorListaIncidencias;


    public IncidenciasFragment() {
        // Required empty public constructor
    }




    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_incidencias, container,false);

        listaIncidencias = view.findViewById(R.id.list_listaIncidencias);

        adaptadorListaIncidencias = new AdaptadorListaIncidencias(getContext(),R.layout.fila_lista_incidencias,miListaIncidencias);
        listaIncidencias.setAdapter(adaptadorListaIncidencias);

        new PeticionesIncidencias.ObtenerTodasLasIncidencias() {
            @Override
            protected void onPostExecute(List<Incidencia> incidencias) {
                super.onPostExecute(incidencias);

                if (incidencias != null && !incidencias.isEmpty()) {
                    Log.d("Incidencia", "Cargando " + incidencias.size() + " incidencias.");


                    miListaIncidencias.clear();


                    for (Incidencia incidencia : incidencias) {

                        incidencia.setImagen(R.drawable.estrella_check_blanco);
                    }


                    miListaIncidencias.addAll(incidencias);

                    // Notificar al adaptador que los datos han cambiado
                    adaptadorListaIncidencias.notifyDataSetChanged();
                } else {
                    Log.d("Incidencia", "No hay incidencias para pintar o la lista es nula.");
                }
            }
        }.execute();



        //Gestionamos los dos eventos al clicar en favoritos y al selecionar un contacto
        adaptadorListaIncidencias.setOnIncidenciaClickListener(new AdaptadorListaIncidencias.OnIncidenciaClickListener() {
            @Override
            public void onIncidenciaClick(Incidencia incidencia) {
                Toast.makeText(getContext(), "Incidencia selecionada: " + incidencia.getCiudad().getNombre(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFavoritoClick(Incidencia incidencia) {
                if (incidencia.getImagen() == R.drawable.estrella_check_blanco) {
                    incidencia.setImagen(R.drawable.estrella_favorito_blanco);
                } else {
                    incidencia.setImagen(R.drawable.estrella_check_blanco);
                }
                adaptadorListaIncidencias.notifyDataSetChanged(); // Refresca la lista para reflejar los cambios.
                Toast.makeText(getContext(), "Favorito actualizado: " + incidencia.getCiudad().getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        listaIncidencias.setAdapter(adaptadorListaIncidencias);




        return view;
    }
}