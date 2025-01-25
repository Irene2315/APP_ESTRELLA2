package com.example.front_android;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.front_android.Adaptadores.AdaptadorListaCamaras;
import com.example.front_android.Adaptadores.AdaptadorListaIncidencias;
import com.example.front_android.Modelos.Camara;
import com.example.front_android.Modelos.FavoritoCamara;
import com.example.front_android.Modelos.FavoritoIncidencia;
import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.PETICIONES_API.PeticionesCamaras;
import com.example.front_android.PETICIONES_API.PeticionesIncidencias;
import com.example.front_android.bdd.GestorBDD;
import com.example.front_android.bdd.GestorIncidenciasFavoritas;

import java.util.ArrayList;
import java.util.List;


public class CamarasFragment extends Fragment {

    private static ListView listaCamaras;
    private static ArrayList<Camara> miListaCamaras = new ArrayList<>();
    private static AdaptadorListaCamaras adaptadorListaCamaras;
    private GestorBDD gestorBDD;
    private List<FavoritoCamara> miListaFavoritosCamaras = new ArrayList<>();




    public CamarasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camaras, container,false);

        listaCamaras = view.findViewById(R.id.list_listaCamaras);

        gestorBDD = new GestorBDD(this.getContext());

        gestorBDD.conectar();

        adaptadorListaCamaras = new AdaptadorListaCamaras(getContext(),R.layout.fila_lista_camaras,miListaCamaras);
        listaCamaras.setAdapter(adaptadorListaCamaras);

        new PeticionesCamaras.ObtenerTodasLasCamaras() {
            @Override
            protected void onPostExecute(List<Camara> camaras) {
                super.onPostExecute(camaras);

                if (camaras != null && !camaras.isEmpty()) {
                    Log.d("Camara", "Cargando " + camaras.size() + " camaras.");


                    miListaCamaras.clear();


                    for (Camara camara : camaras) {

                        camara.setImagen(R.drawable.estrella_check_blanco);
                    }


                    miListaCamaras.addAll(camaras);

                    // Notificar al adaptador que los datos han cambiado
                    adaptadorListaCamaras.notifyDataSetChanged();
                } else {
                    Log.d("Incidencia", "No hay incidencias para pintar o la lista es nula.");
                }
            }
        }.execute();


        miListaFavoritosCamaras = gestorBDD.getGestorCamarasFavoritas().seleccionarTodasLasCamarasFavoritas();
        for (Camara camara : miListaCamaras) {
            for (FavoritoCamara favorito : miListaFavoritosCamaras) {
                if (camara.getId() == favorito.getIdCamara()) {
                    camara.setImagen(R.drawable.estrella_favorito_blanco);
                }
            }
        }

        //Gestionamos los dos eventos al clicar en favoritos y al selecionar un contacto
        adaptadorListaCamaras.setOnIncidenciaClickListener(new AdaptadorListaCamaras.OnCamaraClickListener() {
            @Override
            public void onCamaraClick(Camara camara) {
                Toast.makeText(getContext(), "Camara selecionada: " + camara.getNombre(), Toast.LENGTH_SHORT).show();
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
                adaptadorListaCamaras.notifyDataSetChanged(); // Refresca la lista para reflejar los cambios.
                Toast.makeText(getContext(), "Favorito actualizado: " + camara.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });






        listaCamaras.setAdapter(adaptadorListaCamaras);




        return view;
    }
}