package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Camara;
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

public class PeticionesCamaras {

    private static Camara parseCamara(JSONObject camaraObject) throws JSONException {
        Camara camara = new Camara();


        camara.setId(camaraObject.getInt("id"));
        camara.setNombre(camaraObject.getString("cameraName"));
        camara.setLatitud(camaraObject.getString("latitud"));
        camara.setLongitud(camaraObject.getString("longitud"));


        if (!camaraObject.isNull("urlImagen")) {
            camara.setUrlImagen(camaraObject.getString("urlImagen"));
        } else {
            camara.setUrlImagen(null);
        }


        if (!camaraObject.isNull("region")) {
            JSONObject regionObject = camaraObject.getJSONObject("region");
            Region region = new Region();

            // Parsear los campos de "region"
            region.setId(regionObject.getInt("id"));
            region.setIdRegion(regionObject.getInt("idRegion"));
            region.setNombreEs(regionObject.getString("nombreEs"));
            region.setNombreEu(regionObject.getString("nombreEu"));

            camara.setRegion(region);
        } else {
            camara.setRegion(null);
        }

        return camara;
    }




    public static class ObtenerTodasLasCamaras extends AsyncTask<Void, Void, List<Camara>> {

        @Override
        protected List<Camara> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Camara> camaras = new ArrayList<>();

            try {
                // URL de las incidencias
                URL url = new URL("http://10.10.13.251:8080/privateCameras");
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
                    JSONObject camaraObjet = jsonArray.getJSONObject(i);
                    Camara camara = parseCamara(camaraObjet);
                    camaras.add(camara);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return camaras; // Retorna la lista de incidencias
        }




    }
}
