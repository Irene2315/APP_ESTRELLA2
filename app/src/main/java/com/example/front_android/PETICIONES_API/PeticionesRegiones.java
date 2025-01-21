package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Region;

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

public class PeticionesRegiones {

    private static Region parseRegion(JSONObject regionObject) throws JSONException {
        Region region = new Region();

        region.setId(regionObject.getInt("id"));
        region.setIdRegion(regionObject.getInt("idRegion"));
        region.setNombreEs(regionObject.getString("nombreEs"));
        region.setNombreEu(regionObject.getString("nombreEu"));



        return region;
    }



    public static class ObtenerTodasLasRegiones extends AsyncTask<Void, Void, List<Region>> {

        @Override
        protected List<Region> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Region> regiones = new ArrayList<>();

            try {
                // URL de las incidencias
                URL url = new URL("http://10.10.13.251:8080/privateRegions/soloRegiones");
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
                    JSONObject regionObjet = jsonArray.getJSONObject(i);
                    Region region = parseRegion(regionObjet);
                    regiones.add(region);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return regiones; // Retorna la lista de incidencias
        }

        @Override
        protected void onPostExecute(List<Region> regiones) {
            if (regiones != null) {
                for (Region region : regiones) {
                    Log.d("Region", region.toString());
                }
            }
        }


    }
}
