package com.example.front_android;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.front_android.PETICIONES_API.PeticionesUsuarios;

public class RegistrarTabFragment extends Fragment {

    EditText text_usuario, text_contraseña, text_correo;
    Button registrar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registrar_tab_fragment, container, false);

        text_usuario = view.findViewById(R.id.text_usuario_register);
        text_contraseña = view.findViewById(R.id.text_contraseña_register);
        text_correo = view.findViewById(R.id.text_correo_register);
        registrar = view.findViewById(R.id.btn_registrar);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = text_usuario.getText().toString().trim();
                String contraseña = text_contraseña.getText().toString().trim();
                String correo = text_correo.getText().toString().trim();

                if (usuario.isEmpty() || contraseña.isEmpty() || correo.isEmpty()) {
                    // Validación: Mostrar error si algún campo está vacío
                    Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamar a la tarea asíncrona para registrar al usuario
                PeticionesUsuarios.RegistrarUsuario registrarUsuarioTask = new PeticionesUsuarios.RegistrarUsuario() {
                    @Override
                    protected void onPostExecute(String result) {
                        super.onPostExecute(result);
                        if (result != null) {
                            // Mostrar mensaje de éxito
                            Toast.makeText(getContext(), "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            // Mostrar mensaje de error
                            Toast.makeText(getContext(), "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                // Ejecutar la tarea con los parámetros
                registrarUsuarioTask.execute(usuario, contraseña, correo);
            }
        });



        return view;
    }
}
