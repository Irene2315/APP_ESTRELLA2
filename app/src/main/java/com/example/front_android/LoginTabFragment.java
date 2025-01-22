package com.example.front_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    EditText text_usuario, text_contraseña;
    TextView forget;
    Button entrar;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);

        text_usuario =view.findViewById(R.id.text_usuario);
        text_contraseña = view.findViewById(R.id.text_contraseña);
        forget = view.findViewById(R.id.texto);

        return view;
    }
}
