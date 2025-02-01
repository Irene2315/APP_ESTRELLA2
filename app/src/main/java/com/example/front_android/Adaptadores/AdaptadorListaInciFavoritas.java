package com.example.front_android.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.R;

import java.util.List;

// Adaptador personalizado para mostrar una lista de incidencias favoritas en un ListView
public class AdaptadorListaInciFavoritas extends ArrayAdapter<Incidencia> {

    // Manejar clicks en los elementos de la lista
    public interface OnIncidenciaClickListener {
        void onIncidenciaClick(Incidencia incidencia);
    }

    private List<Incidencia> miListaIncidencias; // Lista de incidencias favoritas
    private Context miContexto; // Contexto de la aplicación
    private int recursosLayout;
    private AdaptadorListaInciFavoritas.OnIncidenciaClickListener listener;

    // Constructor del adaptador
    public AdaptadorListaInciFavoritas(@NonNull Context context, int resource, List<Incidencia> objects) {
        super(context, resource, objects);
        this.miListaIncidencias = objects;
        this.miContexto = context;
        this.recursosLayout = resource;
    }

    // Método para asignar un listener de clicks
    public void setOnIncidenciaClickListener(AdaptadorListaInciFavoritas.OnIncidenciaClickListener listener) {
        this.listener = listener;
    }

    // Método que define cómo se muestra cada incidencia en la lista
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View miVista = convertView;

        if (miVista == null) {
            miVista = LayoutInflater.from(miContexto).inflate(R.layout.fila_lista_inci_favoritas, null);
        }

        Incidencia incidencia = miListaIncidencias.get(position);

        TextView provincia = miVista.findViewById(R.id.texto_provincia);
        provincia.setText(incidencia.getProvincia() != null ? incidencia.getProvincia().getNombre() : "No disponible");

        TextView ciudad = miVista.findViewById(R.id.texto_ciudad);
        ciudad.setText(incidencia.getCiudad() != null ? incidencia.getCiudad().getNombre() : "No disponible");

        TextView tipoIncidencia = miVista.findViewById(R.id.texto_tipo_incidencia);
        tipoIncidencia.setText(incidencia.getTipoIncidencia() != null ? incidencia.getTipoIncidencia().getNombre() : "No disponible");

        // Manejar el click en la incidencia
        miVista.setOnClickListener(v -> {
            if (listener != null) {
                listener.onIncidenciaClick(incidencia);
            }
        });

        return miVista;
    }
}
