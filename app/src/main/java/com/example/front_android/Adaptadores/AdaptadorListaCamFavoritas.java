package com.example.front_android.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.front_android.Modelos.Camara;
import com.example.front_android.R;

import java.util.List;

// Adaptador personalizado para mostrar una lista de cámaras favoritas en un ListView
public class AdaptadorListaCamFavoritas extends ArrayAdapter<Camara> {

    // Manejar clicks en los elementos de la lista
    public interface OnCamaraClickListener {
        void onCamaraClick(Camara camara);
    }

    private List<Camara> miListaCamaras; // Lista de cámaras favoritas
    private Context miContexto; // Contexto de la aplicación
    private int recursosLayout;
    private AdaptadorListaCamFavoritas.OnCamaraClickListener listener;

    // Constructor del Adaptador
    public AdaptadorListaCamFavoritas(@NonNull Context context, int resource, List<Camara> objects) {
        super(context, resource, objects);

        this.miListaCamaras = objects;
        this.miContexto = context;
        this.recursosLayout = resource;
    }

    // Método para asignar un listener de click
    public void setOnCamaraClickListener(AdaptadorListaCamFavoritas.OnCamaraClickListener listener) {
        this.listener = listener;
    }

    // Método que define como se muestra cada cámara en la lista
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View miVista = convertView;

        if (miVista == null) {
            miVista = LayoutInflater.from(miContexto).inflate(R.layout.fila_lista_cam_favoritas, null);
        }

        Camara camara = miListaCamaras.get(position);

        TextView nombreCam = miVista.findViewById(R.id.texto_nombreCam);
        nombreCam.setText(camara.getNombre() != null ? camara.getNombre() : "No disponible");

        TextView region = miVista.findViewById(R.id.texto_region);
        region.setText(camara.getRegion() != null ? camara.getRegion().getNombreEs() : "No disponible");

        // Manejar el click en la cámara
        miVista.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCamaraClick(camara);
            }
        });

        return miVista;
    }
}
