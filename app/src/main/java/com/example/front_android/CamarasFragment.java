package com.example.front_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.front_android.Modelos.Contacto;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

public class CamarasFragment extends Fragment {

    ListView listView;
    private List<Contacto> contactos;

    public CamarasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camaras, container, false);

        listView = view.findViewById(R.id.list_contactos);
        contactos = new ArrayList<>();
        contactos.add(new Contacto("Pepe", "Gómez", "123456789"));
        contactos.add(new Contacto("Juan", "Perez", "987654321"));
        contactos.add(new Contacto("María", "Lopez", "456123789"));
        contactos.add(new Contacto("Ana", "Cespedes", "781569458"));
        contactos.add(new Contacto("Luis", "Carrera", "451523789"));
        contactos.add(new Contacto("Carlos", "Flores", "781256238"));
        contactos.add(new Contacto("Laura", "Rojas", "1151121515"));
        contactos.add(new Contacto("Andrés", "Fernández", "635478921"));
        contactos.add(new Contacto("Carmen", "Navarro", "687451239"));
        contactos.add(new Contacto("Sofía", "Castillo", "689452134"));
        contactos.add(new Contacto("Manuel", "Ortiz", "693214578"));
        contactos.add(new Contacto("John", "Smith", "789654123"));
        contactos.add(new Contacto("Sarah", "Davis", "456789123"));
        contactos.add(new Contacto("David", "Wilson", "987123654"));
        contactos.add(new Contacto("Jessica", "Moore", "741258963"));
        contactos.add(new Contacto("Daniel", "Taylor", "963852741"));
        contactos.add(new Contacto("Elizabeth", "Anderson", "852963741"));

        return view;
    }

}