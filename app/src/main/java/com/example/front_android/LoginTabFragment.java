package com.example.front_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.front_android.PETICIONES_API.PeticionesUsuarios;

public class LoginTabFragment extends Fragment {

    EditText text_usuario, text_contraseña;
    TextView forget;
    Button btn_entrar;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);

        text_usuario =view.findViewById(R.id.text_usuario);
        text_contraseña = view.findViewById(R.id.text_contraseña);
        btn_entrar = view.findViewById(R.id.btn_entrar);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = text_usuario.getText().toString();
                String contraseña = text_contraseña.getText().toString();

                if (usuario.isEmpty() || contraseña.isEmpty()) {
                    Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    new PeticionesUsuarios.LoguearUsuario(getActivity()).execute(usuario, contraseña);
                }
            }
        });


        return view;
    }
}
