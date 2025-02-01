package com.example.front_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.front_android.PETICIONES_API.PeticionesUsuarios;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTabFragment extends Fragment {

    // Variables de los elementos
    EditText text_usuario, text_contraseña;
    TextView forget;
    Button btn_entrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);

        text_usuario =view.findViewById(R.id.text_usuario);
        text_contraseña = view.findViewById(R.id.text_contraseña);
        btn_entrar = view.findViewById(R.id.btn_entrar);

        // Configurar el botón de inicio de sesión
        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = text_usuario.getText().toString();
                String contraseña = text_contraseña.getText().toString();

                // Validación de campos
                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(getActivity(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Llama al método asíncrono
                    new PeticionesUsuarios.LoguearUsuario(getActivity()).execute(usuario, contraseña);
                }
            }
        });
        return view;
    }
}
