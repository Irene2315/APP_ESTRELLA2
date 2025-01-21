package com.example.front_android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient fs;

    public MapaFragment() {
        // Constructor requerido vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Inicializar el cliente de ubicación
        fs = LocationServices.getFusedLocationProviderClient(getContext());

        // Configurar el fragmento del mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map); // Cambiar a getChildFragmentManager()
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), "Error al cargar el mapa", Toast.LENGTH_SHORT).show();
        }

        // Solicitar permisos de geolocalización
        setPermisosGeoloc();

        return view;
    }

    public void obtenerGeolocalizacion() {
        // Verifica si los permisos están concedidos
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obtiene la última ubicación conocida
            fs.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null && map != null) {
                            // Configurar la ubicación actual en el mapa
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        } else {
                            Toast.makeText(getContext(), "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al obtener ubicación: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "Permisos de geolocalización denegados", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPermisosGeoloc() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (map != null) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    map.setMyLocationEnabled(true); // Habilitar el botón de "Mi ubicación"
                    obtenerGeolocalizacion();
                }
            } else {
                Toast.makeText(getContext(), "Permisos denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        // Configura el mapa una vez que está listo
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true); // Habilitar el botón de "Mi ubicación"
            obtenerGeolocalizacion();
        } else {
            setPermisosGeoloc();
        }
    }
}
