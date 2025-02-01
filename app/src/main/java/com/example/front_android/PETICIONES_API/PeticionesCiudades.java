package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Incidencia;

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

public class PeticionesCiudades {

    /**
     * Método que parsea un objeto JSON para crear una instancia de Ciudad.
     */
    private static Ciudad parseCiudad(JSONObject incidenciaObject) throws JSONException {
        Ciudad ciudad = new Ciudad();

        ciudad.setId(incidenciaObject.getInt("id"));
        ciudad.setNombre(incidenciaObject.getString("nombre"));
        ciudad.setLatitud(incidenciaObject.getString("latitud"));
        ciudad.setLongitud(incidenciaObject.getString("longitud"));

        return ciudad;
    }

    /**
     * Método asíncrono para obtener todas las ciudades desde la API.
     */
    public static class ObtenerTodasLasCiudades extends AsyncTask<Void, Void, List<Ciudad>> {

        @Override
        protected List<Ciudad> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Ciudad> ciudades = new ArrayList<>();

            try {
                // URL de las ciudades
                URL url = new URL("http://10.10.13.251:8080/ciudades");
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
                    JSONObject ciudadObject = jsonArray.getJSONObject(i);
                    Ciudad ciudad = parseCiudad(ciudadObject);
                    ciudades.add(ciudad);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return ciudades; // Retorna la lista de ciudades
        }


    }


}
