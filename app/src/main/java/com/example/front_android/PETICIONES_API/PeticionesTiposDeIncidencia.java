package com.example.front_android.PETICIONES_API;

import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Provincia;
import com.example.front_android.Modelos.TipoIncidencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeticionesTiposDeIncidencia {

    private static TipoIncidencia parseTipoIncidencia(JSONObject tipoIncidenciaObject) throws JSONException {
        TipoIncidencia tipoIncidencia = new TipoIncidencia();


        tipoIncidencia.setId(tipoIncidenciaObject.getInt("id"));
        tipoIncidencia.setNombre(tipoIncidenciaObject.getString("nombre"));

        return tipoIncidencia;
    }




}
