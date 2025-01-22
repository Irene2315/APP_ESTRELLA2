package com.example.front_android;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    // Corregido el constructor para inicializar el inflater
    public CustomInfoWindowAdapter(LayoutInflater inflater) {
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
        View v = inflater.inflate(R.layout.infowindow_layout, null);

        // Obtener el título del marcador
        String[] info = m.getTitle().split("&");  // Asumimos que el título contiene "&" como delimitador, ajústalo si es necesario
        String url = m.getSnippet();  // Si necesitas usar el "snippet" también, puedes hacerlo

        // Configurar los textos en el layout inflado
        ((TextView)v.findViewById(R.id.info_window_nombre)).setText(info[0]);  // Ajusta según la estructura de tu título
        ((TextView)v.findViewById(R.id.info_window_placas)).setText("Placas: " + (info.length > 1 ? info[1] : "No disponible"));
        ((TextView)v.findViewById(R.id.info_window_estado)).setText("Estado: Activo");

        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        return null;  // Si no se necesita una ventana personalizada para el marcador, puedes devolver null
    }
}
