package com.example.front_android;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.front_android.Adaptadores.AdaptadorListaCamFavoritas;
import com.example.front_android.Adaptadores.AdaptadorListaCamaras;
import com.example.front_android.Modelos.Camara;
import com.example.front_android.Modelos.FavoritoCamara;
import com.example.front_android.PETICIONES_API.PeticionesCamaras;
import com.example.front_android.bdd.GestorBDD;

import java.util.ArrayList;
import java.util.List;


public class FavoritosCamarasFragment extends Fragment {

    private static ListView listaCamarasFavoritas;
    private static ArrayList<Camara> miListaCamaras = new ArrayList<>();
    private static AdaptadorListaCamFavoritas adaptadorListaCamaras;
    private GestorBDD gestorBDD;
    private List<FavoritoCamara> miListaFavoritosCamaras = new ArrayList<>();
    private List <Camara> camarasFavoritas = new ArrayList<>();
    TextView mensajeSinFavoritos;



    public FavoritosCamarasFragment() {
        // Required empty public constructor
    }




    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camaras, container, false);

        listaCamarasFavoritas = view.findViewById(R.id.list_listaCamaras);
        mensajeSinFavoritos = view.findViewById(R.id.text_no_favoritos);

        gestorBDD = new GestorBDD(this.getContext());
        gestorBDD.conectar();


        adaptadorListaCamaras = new AdaptadorListaCamFavoritas(getContext(), R.layout.fila_lista_cam_favoritas, camarasFavoritas);
        listaCamarasFavoritas.setAdapter(adaptadorListaCamaras);


        miListaFavoritosCamaras = gestorBDD.getGestorCamarasFavoritas().seleccionarTodasLasCamarasFavoritas();


        new PeticionesCamaras.ObtenerTodasLasCamaras() {
            @Override
            protected void onPostExecute(List<Camara> todasLasCamaras) {
                super.onPostExecute(todasLasCamaras);

                if (todasLasCamaras != null && !todasLasCamaras.isEmpty()) {
                    Log.d("Camara", "Cargando " + todasLasCamaras.size() + " cámaras.");

                    camarasFavoritas.clear();


                    for (Camara camara : todasLasCamaras) {
                        for (FavoritoCamara favorito : miListaFavoritosCamaras) {
                            if (Integer.valueOf(camara.getId()).equals(favorito.getIdCamara())) {
                                camarasFavoritas.add(camara);
                                break;
                            }
                        }
                    }


                    adaptadorListaCamaras.notifyDataSetChanged();
                } else {
                    Log.d("Incidencia", "No hay cámaras disponibles o la lista es nula.");
                }

                if (camarasFavoritas.isEmpty()) {
                    mensajeSinFavoritos.setVisibility(View.VISIBLE);
                    listaCamarasFavoritas.setVisibility(View.GONE);
                } else {
                    mensajeSinFavoritos.setVisibility(View.GONE);
                    listaCamarasFavoritas.setVisibility(View.VISIBLE);
                }
            }
        }.execute();

        return view;
    }

}