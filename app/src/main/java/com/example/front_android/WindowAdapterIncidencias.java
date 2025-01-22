package com.example.front_android;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.front_android.Modelos.Incidencia;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class WindowAdapterIncidencias implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    // Corregido el constructor para inicializar el inflater
    public WindowAdapterIncidencias(LayoutInflater inflater) {
        this.inflater = inflater;  // Asigna el inflater pasado al campo de la clase
    }

    @Override
    public View getInfoContents(final Marker m) {
        // Verifica si inflater es null para evitar el NullPointerException
        if (inflater == null) {
            Log.e(TAG, "LayoutInflater is null");
            return null;
        }

        // Inflar el layout personalizado
        View v = inflater.inflate(R.layout.infowindow_incidencias, null);

        Incidencia incidencia = (Incidencia) m.getTag();

        if (incidencia != null) {

            ((TextView) v.findViewById(R.id.info_window_carretera)).setText("Carretera: " + incidencia.getCarretera());

            // Placas
            String ciudadNombre = incidencia.getCiudad() != null ? incidencia.getCiudad().getNombre() : "No disponible";
            ((TextView) v.findViewById(R.id.info_window_ciudad)).setText("Ciudad: " + ciudadNombre);

            // Estado
            String tipoIncidenciaNombre = incidencia.getTipoIncidencia() != null ? incidencia.getTipoIncidencia().getNombre() : "No especificado";
            ((TextView) v.findViewById(R.id.info_window_tipoI)).setText("Incidencia: " + tipoIncidenciaNombre);
        }
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;  // Si no se necesita una ventana personalizada para el marcador, puedes devolver null
    }
}
