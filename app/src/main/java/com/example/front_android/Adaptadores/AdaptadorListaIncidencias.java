package com.example.front_android.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.R;

import java.util.List;

public class AdaptadorListaIncidencias extends ArrayAdapter<Incidencia> {


    public interface OnIncidenciaClickListener {
        void onIncidenciaClick(Incidencia incidencia);
        void onFavoritoClick(Incidencia incidencia);
    }


    private List<Incidencia> miListaIncidencias;
    //Definimos el contexto y recurso a utilizar
    private Context miContexto;
    private int recursosLayout;
    private OnIncidenciaClickListener listener;

    public AdaptadorListaIncidencias(@NonNull Context context, int resource, List<Incidencia> objects) {
        super(context, resource, objects);
        this.miListaIncidencias = objects;
        this.miContexto = context;
        this.recursosLayout = resource;
    }

    public void setOnContactoClickListener(OnIncidenciaClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View miVista = convertView;

        if(miVista == null){
            miVista = LayoutInflater.from(miContexto).inflate(R.layout.fila_lista_incidencias,null);

        }

        // Definimos como se va a construir el objeto en este adaptador
        Incidencia incidencia = miListaIncidencias.get(position);


        TextView provincia = miVista.findViewById(R.id.texto_provincia);
        provincia.setText(incidencia.getProvincia().getNombre());

        TextView ciudad = miVista.findViewById(R.id.texto_ciudad);
        ciudad.setText(incidencia.getCiudad().getNombre());

        TextView tipoIncidencia = miVista.findViewById(R.id.texto_tipo_incidencia);
        ciudad.setText(incidencia.getCiudad().getNombre());


        ImageButton imagen = miVista.findViewById(R.id.img);
        imagen.setImageResource(incidencia.getImagen());


        imagen.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFavoritoClick(incidencia);
            }
        });

        // Manejar el clic en el elemento completo
        miVista.setOnClickListener(v -> {
            if (listener != null) {
                listener.onIncidenciaClick(incidencia);
            }
        });

        return miVista;
    }
}