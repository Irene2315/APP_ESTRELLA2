package com.example.front_android.Adaptadores;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.front_android.Modelos.Camara;
import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class WindowAdapterUniversal implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "WindowAdapterUniversal";
    private final LayoutInflater inflater;
    private OnCamaraClickListener listenerCamara;
    private OnIncidenciaClickListener listenerIncidencia;

    // Interfaces para manejar los clics
    public interface OnCamaraClickListener {
        void onCamaraClick(Camara camara);
        void onFavoritoCamaraClick(Camara camara);
    }

    public interface OnIncidenciaClickListener {
        void onIncidenciaClick(Incidencia incidencia);
        void onFavoritoIncidenciaClick(Incidencia incidencia);
    }

    public void setOnCamaraClickListener(OnCamaraClickListener listenerCamara) {
        this.listenerCamara = listenerCamara;
    }

    public void setOnIncidenciaClickListener(OnIncidenciaClickListener listenerIncidencia) {
        this.listenerIncidencia = listenerIncidencia;
    }

    public WindowAdapterUniversal(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        if (inflater == null) {
            Log.e(TAG, "LayoutInflater is null");
            return null;
        }

        View view;
        try {
            view = inflater.inflate(R.layout.infowindow_universal, null);
        } catch (Exception e) {
            Log.e(TAG, "Error inflating view", e);
            return null;
        }

        Object tag = marker.getTag();
        if (tag instanceof Camara) {
            gestionCamaras(view, (Camara) tag);
        } else if (tag instanceof Incidencia) {
            gestionIncidencias(view, (Incidencia) tag);
        }

        return view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null; // No personalizar completamente la ventana
    }

    private void gestionCamaras(View view, Camara camara) {
        if (camara == null) return;


        TextView nombreView = view.findViewById(R.id.info_window_carretera);
        nombreView.setText("Nombre: " + (camara.getNombre() != null ? camara.getNombre() : "No disponible"));

        TextView regionView = view.findViewById(R.id.info_window_ciudad);
        String regionNombre = camara.getRegion() != null && camara.getRegion().getNombreEs() != null
                ? camara.getRegion().getNombreEs() : "No disponible";
        regionView.setText("Región: " + regionNombre);

        ImageButton favoritosButton = view.findViewById(R.id.favoritos);
        favoritosButton.setImageResource(camara.getImagen());
        favoritosButton.setOnClickListener(v -> {
            Log.i("Favorito","Favorito_camara_clicado");
            if (listenerCamara != null) {
                listenerCamara.onFavoritoCamaraClick(camara);
            }
        });

        ImageButton masInfoButton = view.findViewById(R.id.mas_info);
        masInfoButton.setOnClickListener(v -> {
            Log.i("MasI","Mas info clicado");
            if (listenerCamara != null) {
                listenerCamara.onCamaraClick(camara);
            }
        });
    }

    private void gestionIncidencias(View view, Incidencia incidencia) {
        if (incidencia == null) return;


        TextView carreteraView = view.findViewById(R.id.info_window_carretera);
        carreteraView.setText("Carretera: " + (incidencia.getCarretera() != null ? incidencia.getCarretera() : "No disponible"));


        TextView ciudadView = view.findViewById(R.id.info_window_ciudad);
        String ciudadNombre = incidencia.getCiudad() != null && incidencia.getCiudad().getNombre() != null
                ? incidencia.getCiudad().getNombre() : "No disponible";
        ciudadView.setText("Ciudad: " + ciudadNombre);


        TextView tipoView = view.findViewById(R.id.info_window_tipoI);
        String tipoIncidenciaNombre = incidencia.getTipoIncidencia() != null && incidencia.getTipoIncidencia().getNombre() != null
                ? incidencia.getTipoIncidencia().getNombre() : "No especificado";
        tipoView.setText("Incidencia: " + tipoIncidenciaNombre);

        ImageButton favoritosButton = view.findViewById(R.id.favoritos);
        favoritosButton.setImageResource(incidencia.getImagen());
        favoritosButton.setOnClickListener(v -> {
            Log.i("Favorito","Favorito_camara_clicado");
            if (listenerIncidencia != null) {
                listenerIncidencia.onFavoritoIncidenciaClick(incidencia);
            }
        });

        ImageButton masInfoButton = view.findViewById(R.id.mas_info);
        masInfoButton.setOnClickListener(v -> {
            Log.i("MasI","Mas info clicado");
            if (listenerIncidencia != null) {
                listenerIncidencia.onIncidenciaClick(incidencia);
            }
        });
    }
}
