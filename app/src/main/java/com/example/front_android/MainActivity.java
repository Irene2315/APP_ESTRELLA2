package com.example.front_android;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    GoogleMap map;
    FusedLocationProviderClient fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.abrir_menu_nav, R.string.cerrar_menu_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        fs = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        setPermisosGeoloc();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(this, "adios", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String message = "";

        switch (id) {
            case R.id.nav_mapa:
                message = "Mapa seleccionado";
                break;
            case R.id.nav_camaras:
                message = "Cámaras seleccionado";
                break;
            case R.id.nav_incidencias:
                message = "Incidencias seleccionado";
                break;
            case R.id.nav_favoritos:
                message = "Favoritos seleccionado";
                break;
            case R.id.nav_infoApp:
                message = "Información de la app seleccionada";
                break;
            case R.id.nav_perfil:
                message = "Perfil seleccionado";
                break;
            case R.id.nav_cerrar_sesion:
                message = "Cerrar sesión seleccionado";
                break;
            default:
                message = "Opción no reconocida";
        }

        // Mostrar el mensaje si existe
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        // Cerrar el Drawer después de seleccionar
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void obtenerGeolocalizacion() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fs.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {

                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            map.addMarker(new MarkerOptions().position(currentLocation).title("Ubicación actual"));
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error al obtener ubicación: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "Permisos de geolocalización denegados", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPermisosGeoloc() {
        int geoPermisos = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (geoPermisos == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permisos de geolocalización concedido", Toast.LENGTH_SHORT).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
            }
            Toast.makeText(this, "Permisos de geolocalización denegados", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        obtenerGeolocalizacion();
    }


}
