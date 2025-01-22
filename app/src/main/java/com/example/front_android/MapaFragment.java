package com.example.front_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.PETICIONES_API.PeticionesIncidencias;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private FusedLocationProviderClient fs;
    private List<Incidencia> miListaIncidencias = new ArrayList<>();

    public MapaFragment() {
        // Constructor requerido vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        fs = LocationServices.getFusedLocationProviderClient(getContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(getContext(), "Error al cargar el mapa", Toast.LENGTH_SHORT).show();
        }


        setPermisosGeoloc();



        return view;
    }

    public void obtenerGeolocalizacion() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fs.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null && map != null) {
                            // Configurar la ubicación actual en el mapa
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10));
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

    @SuppressLint("StaticFieldLeak")
    public void pintarIncidencias() {


        // Obtener las incidencias de la API
        new PeticionesIncidencias.ObtenerTodasLasIncidencias() {
            @Override
            protected void onPostExecute(List<Incidencia> incidencias) {
                super.onPostExecute(incidencias);

                if (map == null) {
                    Log.e("Error", "Mapa no inicializado en onPostExecute.");
                    return;
                }

                if (incidencias != null && !incidencias.isEmpty()) {
                    miListaIncidencias.clear();
                    miListaIncidencias.addAll(incidencias);

                    for (Incidencia incidencia : miListaIncidencias) {
                        try {
                            double lat = Double.parseDouble(incidencia.getLatitud());
                            double lng = Double.parseDouble(incidencia.getLongitud());
                            LatLng punto = new LatLng(lat, lng);
                            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.incidencias);

                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 50, 50, false);


                            map.setInfoWindowAdapter(new CustomInfoWindowAdapter(getLayoutInflater()));
                            map.addMarker(new MarkerOptions()
                                    .position(punto)
                                    .title("Ciudad: " + incidencia.getId())
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap)));
                        } catch (NumberFormatException e) {
                            Log.e("Error", "Coordenadas inválidas para la incidencia: " + incidencia.getId(), e);
                        }
                    }
                } else {
                    Log.d("Incidencia", "No hay incidencias para pintar.");
                }
            }
        }.execute();
        }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        pintarIncidencias();


        map.getUiSettings().setZoomControlsEnabled(true);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            //obtenerGeolocalizacion();



        } else {
            setPermisosGeoloc();
        }
    }
}
