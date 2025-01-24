package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Provincia;

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

public class PeticionesProvincias {

    private static Provincia parseProvincia(JSONObject provinciaObject) throws JSONException {
        Provincia provincia = new Provincia();

        // Obtener los valores básicos del objeto Provincia
        provincia.setId(provinciaObject.getInt("id"));
        provincia.setNombre(provinciaObject.getString("nombre"));
        provincia.setLatitud(provinciaObject.getString("latitud"));
        provincia.setLongitud(provinciaObject.getString("longitud"));

        // Obtener el JSONArray de las ciudades
        JSONArray ciudadesArray = provinciaObject.getJSONArray("ciudad");

        // Crear una lista de ciudades
        List<Ciudad> ciudades = new ArrayList<>();

        // Recorrer el JSONArray y crear objetos Ciudad
        for (int i = 0; i < ciudadesArray.length(); i++) {
            JSONObject ciudadObject = ciudadesArray.getJSONObject(i);

            // Crear una instancia de Ciudad y setear sus valores
            Ciudad ciudad = new Ciudad();
            ciudad.setId(ciudadObject.getInt("id"));
            ciudad.setNombre(ciudadObject.getString("nombre"));
            ciudad.setLatitud(ciudadObject.getString("latitud"));
            ciudad.setLongitud(ciudadObject.getString("longitud"));

            // Añadir la ciudad a la lista
            ciudades.add(ciudad);
        }

        // Asignar la lista de ciudades al objeto Provincia
        provincia.setCiudades(ciudades);

        // Devolver el objeto Provincia con las ciudades
        return provincia;
    }


    public static class ObtenerTodasLasProvincias extends AsyncTask<Void, Void, List<Provincia>> {

        @Override
        protected List<Provincia> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Provincia> provincias = new ArrayList<>();

            try {
                // URL de las incidencias
                URL url = new URL("http://10.10.13.251:8080/provincias");
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
                    JSONObject provinciaObject = jsonArray.getJSONObject(i);
                    Provincia provincia = parseProvincia(provinciaObject);
                    provincias.add(provincia);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return provincias; // Retorna la lista de incidencias
        }



    }


}
