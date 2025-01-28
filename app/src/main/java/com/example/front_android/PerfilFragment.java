package com.example.front_android;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.front_android.Modelos.Usuario;
import com.example.front_android.PETICIONES_API.PeticionesUsuarios;


public class PerfilFragment extends Fragment {

    private TextView nombreUsuario;
    private TextView correoUsuario;

    public PerfilFragment() {
        // Required empty public constructor
    }




    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        nombreUsuario = view.findViewById(R.id.nombre_usuario);
        correoUsuario = view.findViewById(R.id.correo_usuario);

        SharedPreferences preferences = getContext().getSharedPreferences("app_localDatos", MODE_PRIVATE);

        // Obtener el idUsuario y convertirlo a int
        String idUsuarioString = preferences.getString("idUsuario", "default");

        int idUsuario = -1; // Valor por defecto si no es válido

        // Intentar convertir el String a un int
        try {
            idUsuario = Integer.parseInt(idUsuarioString);
        } catch (NumberFormatException e) {
            Log.e("Usuario", "Error al convertir idUsuario a int: " + e.getMessage());
        }

        Log.d("Usuario", "ID de usuario obtenido: " + idUsuario);

        // Verifica si el idUsuario es válido (es decir, no es -1 o el valor por defecto)
        if (idUsuario != -1) {
            Log.d("Usuario", "ID de usuario obtenido: " + idUsuario);

            // Realizar la petición para obtener el usuario
            new PeticionesUsuarios.ObtenerUnUsuario() {
                @Override
                protected void onPostExecute(Usuario usuarioSesion) {
                    if (usuarioSesion != null) {
                        Log.d("Usuario", usuarioSesion.toString());
                        nombreUsuario.setText(usuarioSesion.getNombre());
                        correoUsuario.setText(usuarioSesion.getCorreoElectronico());

                    } else {
                        Log.e("Usuario", "No se pudo obtener el usuario.");
                    }
                }
            }.execute(idUsuario);
        } else {
            Log.e("Usuario", "El ID de usuario es nulo o inválido.");
            // Aquí puedes manejar la situación cuando no hay ID guardado, como mostrar un mensaje o redirigir al login
        }


        return view;
    }

}