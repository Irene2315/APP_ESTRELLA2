package com.example.front_android;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.front_android.Adaptadores.AdaptadorListaInciFavoritas;
import com.example.front_android.Adaptadores.AdaptadorListaIncidencias;
import com.example.front_android.Modelos.FavoritoIncidencia;
import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.PETICIONES_API.PeticionesIncidencias;
import com.example.front_android.bdd.GestorBDD;

import java.util.ArrayList;
import java.util.List;


public class FavoritosIncidenciasFragment extends Fragment {

    private static ListView listaIncidenciasFavoritas;
    private static ArrayList<Incidencia> miListaIncidencias = new ArrayList<>();
    private static AdaptadorListaInciFavoritas adaptadorListaIncidencias;
    private GestorBDD gestorBDD;
    private List<FavoritoIncidencia> miListaFavoritosIncidencias = new ArrayList<>();
    private List <Incidencia> incidenciasFavoritas = new ArrayList<>();
    TextView mensajeSinFavoritos;



    public FavoritosIncidenciasFragment() {
        // Required empty public constructor
    }



    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos_incidencias, container,false);

        listaIncidenciasFavoritas = view.findViewById(R.id.list_listaIncidencias);
        mensajeSinFavoritos = view.findViewById(R.id.text_no_favoritos);

        gestorBDD = new GestorBDD(this.getContext());

        gestorBDD.conectar();

        adaptadorListaIncidencias = new AdaptadorListaInciFavoritas(getContext(),R.layout.fila_lista_inci_favoritas,incidenciasFavoritas);
        listaIncidenciasFavoritas.setAdapter(adaptadorListaIncidencias);

        miListaFavoritosIncidencias = gestorBDD.getGestorIncidenciasFavoritas().seleccionarTodasLasIncidenciasFavoritas();

        new PeticionesIncidencias.ObtenerTodasLasIncidencias() {
            @Override
            protected void onPostExecute(List<Incidencia> incidencias) {
                super.onPostExecute(incidencias);

                if (incidencias != null && !incidencias.isEmpty()) {
                    Log.d("Incidencia", "Cargando " + incidencias.size() + " incidencias.");


                    incidenciasFavoritas.clear();


                    for (Incidencia incidencia : incidencias) {

                       for(FavoritoIncidencia favoritoIncidencia: miListaFavoritosIncidencias){
                           if (Integer.valueOf(incidencia.getId()).equals(favoritoIncidencia.getIdIncidencia())) {
                               incidenciasFavoritas.add(incidencia);
                               break;
                           }
                       }
                    }

                    adaptadorListaIncidencias.notifyDataSetChanged();
                } else {
                    Log.d("Incidencia", "No hay incidencias para pintar o la lista es nula.");
                }
                if (incidenciasFavoritas.isEmpty()) {
                    mensajeSinFavoritos.setVisibility(View.VISIBLE);
                    listaIncidenciasFavoritas.setVisibility(View.GONE);
                } else {
                    mensajeSinFavoritos.setVisibility(View.GONE);
                    listaIncidenciasFavoritas.setVisibility(View.VISIBLE);
                }
            }
        }.execute();



        adaptadorListaIncidencias.setOnIncidenciaClickListener(new AdaptadorListaInciFavoritas.OnIncidenciaClickListener() {
            @Override
            public void onIncidenciaClick(Incidencia incidencia) {
                Toast.makeText(getContext(), "Incidencia selecionada: " + incidencia.getCiudad().getNombre(), Toast.LENGTH_SHORT).show();


                IncidenciaFragment incidenciaFragment = IncidenciaFragment.newInstance(incidencia);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, incidenciaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }


        });


        return view;
    }
}