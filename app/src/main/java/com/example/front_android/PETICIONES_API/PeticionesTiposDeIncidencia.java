package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Provincia;
import com.example.front_android.Modelos.TipoIncidencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PeticionesTiposDeIncidencia {

    private static TipoIncidencia parseTipoIncidencia(JSONObject tipoIncidenciaObject) throws JSONException {
        TipoIncidencia tipoIncidencia = new TipoIncidencia();


        tipoIncidencia.setId(tipoIncidenciaObject.getInt("id"));
        tipoIncidencia.setNombre(tipoIncidenciaObject.getString("nombre"));

        return tipoIncidencia;
    }

    public static class ObtenerTodasLosTiposDeIncidencia extends AsyncTask<Void, Void, List<TipoIncidencia> >{

        @Override
        protected List<TipoIncidencia> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<TipoIncidencia> tiposIncidencias = new ArrayList<>();

            try {
                // URL de las incidencias
                URL url = new URL("http://10.10.13.251:8080/tiposIncidencias");
                urlConnection = (HttpURLConnection) url.openConnection();

                // Verificar código de respuesta
                int code = urlConnection.getResponseCode();
                if (code != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Respuesta inválida del servidor: " + code);
                }

                // Leer la respuesta del servidor
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            // Parsear el JSON
            try {
                JSONArray jsonArray = new JSONArray(jsonResult.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tipoDeIncidenciaObject = jsonArray.getJSONObject(i);
                    TipoIncidencia tipoIncidencia = parseTipoIncidencia(tipoDeIncidenciaObject);
                    tiposIncidencias.add(tipoIncidencia);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return tiposIncidencias; // Retorna la lista de incidencias
        }

        @Override
        protected void onPostExecute(List<TipoIncidencia> tipoIncidencias) {
            if (tipoIncidencias != null) {
                for (TipoIncidencia tipoIncidencia : tipoIncidencias) {
                    Log.d("TipoIncidencia", tipoIncidencia.toString());
                }
            }
        }


    }







}
