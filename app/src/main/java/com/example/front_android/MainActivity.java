package com.example.front_android;


import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.front_android.Modelos.Usuario;

import com.example.front_android.PETICIONES_API.PeticionesCamaras;
import com.example.front_android.PETICIONES_API.PeticionesIncidencias;

import com.example.front_android.PETICIONES_API.PeticionesUsuarios;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Variables de los elementos y fragmentos
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    MapaFragment mapaFragment = new MapaFragment();
    CamarasFragment camarasFragment = new CamarasFragment();
    IncidenciasFragment incidenciasFragment = new IncidenciasFragment();
    FavoritosCamarasFragment favoritosCamarasFragment = new FavoritosCamarasFragment();
    FavoritosIncidenciasFragment favoritosIncidenciasFragment = new FavoritosIncidenciasFragment();
    InfoAppFragment infoAppFragment = new InfoAppFragment();
    PerfilFragment perfilFragment = new PerfilFragment();

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

        // Configuración del menú lateral (DrawerLayout) con el ActionBar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.abrir_menu_nav, R.string.cerrar_menu_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Establecer el listener para los elementos del menú de navegación
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container,mapaFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        // Si el menú lateral está abierto, lo cierra en lugar de salir de la app
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
        // Obtiene el ID del ítem seleccionado
        int id = item.getItemId();
        String message = "";

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (id) {
            case R.id.nav_mapa:
                message = "Mapa seleccionado";

                new PeticionesIncidencias.ObtenerIncidenciasRegion().execute();
                new PeticionesCamaras.ObtenerTodasLasCamaras().execute();
                fragmentTransaction.replace(R.id.fragment_container,mapaFragment);
                break;

            case R.id.nav_camaras:
                ArrayList<Usuario> miListaUsuarios = new ArrayList<Usuario>();
                message = "Cámaras seleccionado";
                fragmentTransaction.replace(R.id.fragment_container,camarasFragment);

                break;
            case R.id.nav_incidencias:
                message = "Incidencias seleccionado";
                fragmentTransaction.replace(R.id.fragment_container,incidenciasFragment);
                break;
            case R.id.nav_favoritos_camaras:
                message = "Favoritos seleccionado";
                fragmentTransaction.replace(R.id.fragment_container, favoritosCamarasFragment);
                break;
            case R.id.nav_favoritos_incidencias:
                message = "Favoritos seleccionado";
                fragmentTransaction.replace(R.id.fragment_container,favoritosIncidenciasFragment );
                break;
            case R.id.nav_infoApp:
                message = "Información de la app seleccionada";
                fragmentTransaction.replace(R.id.fragment_container,infoAppFragment);
                break;
            case R.id.nav_perfil:
                message = "Perfil seleccionado";
                fragmentTransaction.replace(R.id.fragment_container,perfilFragment);
                break;
            case R.id.nav_cerrar_sesion:
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;

            default:
                message = "Opción no reconocida";
        }
        fragmentTransaction.commit();

        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        // Cerrar el Drawer después de seleccionar
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}