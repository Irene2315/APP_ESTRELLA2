package com.example.front_android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

    GoogleMap map;
    FusedLocationProviderClient fs;

    public MapaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fs = LocationServices.getFusedLocationProviderClient(getContext());


        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);
        }

        setPermisosGeoloc();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    public void obtenerGeolocalizacion() {
        // Verifica si el permiso está concedido antes de intentar obtener la ubicación
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fs.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Error al obtener ubicación: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(getContext(), "Permisos de geolocalización denegados", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPermisosGeoloc() {
        // Verifica si el permiso ya está concedido
        int geoPermisos = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (geoPermisos == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permisos de geolocalización concedido", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            } else {
                Toast.makeText(getContext(), "Permisos de geolocalización denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método que maneja la respuesta de los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Permisos concedidos", Toast.LENGTH_SHORT).show();
                obtenerGeolocalizacion();
            } else {
                Toast.makeText(getContext(), "Permisos denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        obtenerGeolocalizacion();
    }
}
