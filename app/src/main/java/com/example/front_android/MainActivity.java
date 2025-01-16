package com.example.front_android;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.appcompat.widget.Toolbar;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);


       navigationView.bringChildToFront();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.abrir_menu_nav, R.string.cerrar_menu_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
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

}
