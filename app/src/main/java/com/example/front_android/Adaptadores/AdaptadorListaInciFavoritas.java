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

public class AdaptadorListaInciFavoritas extends ArrayAdapter<Incidencia> {

    public interface OnIncidenciaClickListener {
        void onIncidenciaClick(Incidencia incidencia);
    }


    private List<Incidencia> miListaIncidencias;
    //Definimos el contexto y recurso a utilizar
    private Context miContexto;
    private int recursosLayout;
    private AdaptadorListaInciFavoritas.OnIncidenciaClickListener listener;

    public AdaptadorListaInciFavoritas(@NonNull Context context, int resource, List<Incidencia> objects) {
        super(context, resource, objects);
        this.miListaIncidencias = objects;
        this.miContexto = context;
        this.recursosLayout = resource;
    }

    public void setOnIncidenciaClickListener(AdaptadorListaInciFavoritas.OnIncidenciaClickListener listener) {
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
        provincia.setText(incidencia.getProvincia() != null ? incidencia.getProvincia().getNombre() : "No disponible");


        TextView ciudad = miVista.findViewById(R.id.texto_ciudad);
        ciudad.setText(incidencia.getCiudad() != null ? incidencia.getCiudad().getNombre() : "No disponible");


        TextView tipoIncidencia = miVista.findViewById(R.id.texto_tipo_incidencia);
        tipoIncidencia.setText(incidencia.getTipoIncidencia() != null ? incidencia.getTipoIncidencia().getNombre() : "No disponible");





        // Manejar el clic en el elemento completo
        miVista.setOnClickListener(v -> {
            if (listener != null) {
                listener.onIncidenciaClick(incidencia);
            }
        });

        return miVista;
    }
}
