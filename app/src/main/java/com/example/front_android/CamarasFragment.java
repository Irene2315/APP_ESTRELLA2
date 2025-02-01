package com.example.front_android;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.front_android.Adaptadores.AdaptadorListaCamaras;
import com.example.front_android.Modelos.Camara;
import com.example.front_android.Modelos.FavoritoCamara;
import com.example.front_android.PETICIONES_API.PeticionesCamaras;
import com.example.front_android.bdd.GestorBDD;

import java.util.ArrayList;
import java.util.List;


public class CamarasFragment extends Fragment {

    // Variables para las cámaras
    private static ListView listaCamaras;
    private static ArrayList<Camara> miListaCamaras = new ArrayList<>();
    private static AdaptadorListaCamaras adaptadorListaCamaras;
    private GestorBDD gestorBDD;
    private List<FavoritoCamara> miListaFavoritosCamaras = new ArrayList<>();
    private CamaraFragment camaraFragment;

    public CamarasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camaras, container, false);

        listaCamaras = view.findViewById(R.id.list_listaCamaras);

        // Conexión a la base de datos
        gestorBDD = new GestorBDD(this.getContext());
        gestorBDD.conectar();

        adaptadorListaCamaras = new AdaptadorListaCamaras(getContext(), R.layout.fila_lista_camaras, miListaCamaras);
        listaCamaras.setAdapter(adaptadorListaCamaras);

        // Obtener la lista de cámaras favoritas desde la base de datos
        miListaFavoritosCamaras = gestorBDD.getGestorCamarasFavoritas().seleccionarTodasLasCamarasFavoritas();


        // Petición para obtener todas las cámaras desde la API
        new PeticionesCamaras.ObtenerTodasLasCamaras() {
            @Override
            protected void onPostExecute(List<Camara> camaras) {
                super.onPostExecute(camaras);

                if (camaras != null && !camaras.isEmpty()) {
                    Log.d("Camara", "Cargando " + camaras.size() + " cámaras.");
                    miListaCamaras.clear();

                    for (Camara camara : camaras) {
                        // Verificar si la cámara es Favorita
                        boolean esFavorito = false;
                        for (FavoritoCamara favorito : miListaFavoritosCamaras) {
                            if (camara.getId() == favorito.getIdCamara()) {
                                esFavorito = true;
                                break;
                            }
                        }
                        // Asignación de una imagen si es favorito o no
                        if (esFavorito) {
                            camara.setImagen(R.drawable.estrella_favorito_blanco);
                        } else {
                            camara.setImagen(R.drawable.estrella_check_blanco);
                        }
                        miListaCamaras.add(camara);
                    }
                    adaptadorListaCamaras.notifyDataSetChanged();
                } else {
                    Log.d("Camara", "No se obtuvieron cámaras de la API.");
                }
            }
        }.execute();


        // Manejador de los clicks sobre la lista de la cámara
        adaptadorListaCamaras.setOnCamaraClickListener(new AdaptadorListaCamaras.OnCamaraClickListener() {

            @Override
            public void onCamaraClick(Camara camara) {
                Toast.makeText(getContext(), "Cámara seleccionada: " + camara.getNombre(), Toast.LENGTH_SHORT).show();
                CamaraFragment camaraFragment = CamaraFragment.newInstance(camara);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, camaraFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }


            @Override
            public void onFavoritoClick(Camara camara) {

                if (camara.getImagen() == R.drawable.estrella_check_blanco) {
                    camara.setImagen(R.drawable.estrella_favorito_blanco);
                    gestorBDD.getGestorCamarasFavoritas().insertarFavoritosCamaras(String.valueOf(camara.getId()));
                } else {
                    camara.setImagen(R.drawable.estrella_check_blanco);
                    gestorBDD.getGestorCamarasFavoritas().eliminarFavoritosCamaras(String.valueOf(camara.getId()));
                }

                adaptadorListaCamaras.notifyDataSetChanged();

                Toast.makeText(getContext(), "Favorito actualizado: " + camara.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}