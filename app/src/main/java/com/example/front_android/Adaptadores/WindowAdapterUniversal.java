package com.example.front_android.Adaptadores;

import android.content.Context;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class WindowAdapterUniversal implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "WindowAdapterUniversal";
    private final LayoutInflater inflater;
    private OnCamaraClickListener listenerCamara;
    private OnIncidenciaClickListener listenerIncidencia;
    private Context context;

    // Interfaces para manejar los clics en los elementos
    public interface OnCamaraClickListener {
        void onCamaraClick(Camara camara);
        void onFavoritoCamaraClick(Camara camara);
    }

    public interface OnIncidenciaClickListener {
        void onIncidenciaClick(Incidencia incidencia);
        void onFavoritoIncidenciaClick(Incidencia incidencia);
    }

    // Métodos para asignar los listeners
    public void setOnCamaraClickListener(OnCamaraClickListener listenerCamara) {
        this.listenerCamara = listenerCamara;
    }

    public void setOnIncidenciaClickListener(OnIncidenciaClickListener listenerIncidencia) {
        this.listenerIncidencia = listenerIncidencia;
    }

    // Constructor del adaptador
    public WindowAdapterUniversal(Context context, LayoutInflater inflater) {
        this.context = context;
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

        // Identifica el tipo de objeto asociado al marcador
        Object tag = marker.getTag();
        if (tag instanceof Camara) {
            mostrarBottomSheetCamara((Camara) tag);
            gestionCamaras(view, (Camara) tag);
        } else if (tag instanceof Incidencia) {
            mostrarBottomSheetIncidencia((Incidencia) tag);
            gestionIncidencias(view, (Incidencia) tag);
        }

        return view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    // Configuración del tooltip de una cámara
    public void gestionCamaras(View view, Camara camara) {
        if (camara == null) return;

        ImageButton btnFavorito = view.findViewById(R.id.favoritos);
        ImageButton btnMasInfo = view.findViewById(R.id.mas_info);
        TextView nombreView = view.findViewById(R.id.info_window_carretera);
        nombreView.setText("Nombre: " + (camara.getNombre() != null ? camara.getNombre() : "No disponible"));

        TextView regionView = view.findViewById(R.id.info_window_ciudad);
        String regionNombre = camara.getRegion() != null && camara.getRegion().getNombreEs() != null
                ? camara.getRegion().getNombreEs() : "No disponible";
        regionView.setText("Región: " + regionNombre);

        btnFavorito.setOnClickListener(v -> {
            if (listenerCamara != null) listenerCamara.onFavoritoCamaraClick(camara);
        });

        btnMasInfo.setOnClickListener(v -> {
            if (listenerCamara != null) listenerCamara.onCamaraClick(camara);
        });
    }

    // Muestra un BottomSheet con la información de la cámara
    public void mostrarBottomSheetCamara(Camara camara) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = inflater.inflate(R.layout.infowindow_universal, null);

        gestionCamaras(view, camara);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    // Configuración del tooltip de una incidencia
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

        ImageButton btnFavorito = view.findViewById(R.id.favoritos);
        ImageButton btnMasInfo = view.findViewById(R.id.mas_info);

        btnFavorito.setOnClickListener(v -> {
            if (listenerIncidencia != null) listenerIncidencia.onFavoritoIncidenciaClick(incidencia);
        });

        btnMasInfo.setOnClickListener(v -> {
            if (listenerIncidencia != null) listenerIncidencia.onIncidenciaClick(incidencia);
        });
    }

    // Muestra un BottomSheet con la información de la incidencia
    public void mostrarBottomSheetIncidencia(Incidencia incidencia) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = inflater.inflate(R.layout.infowindow_universal, null);

        gestionIncidencias(view, incidencia);

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}