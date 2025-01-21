package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Ciudad;
import com.example.front_android.Modelos.Incidencia;
import com.example.front_android.Modelos.Provincia;
import com.example.front_android.Modelos.Region;
import com.example.front_android.Modelos.TipoIncidencia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PeticionesIncidencias {



    private static Incidencia parseIncidencia(JSONObject incidenciaObject) throws JSONException {
        Incidencia incidencia = new Incidencia();

        incidencia.setId(incidenciaObject.getInt("id"));
        incidencia.setLatitud(incidenciaObject.getString("latitud"));
        incidencia.setLongitud(incidenciaObject.getString("longitud"));
        incidencia.setCausa(incidenciaObject.getString("causa"));
        incidencia.setNivelIncidencia(incidenciaObject.getString("nivelIncidencia"));
        incidencia.setCarretera(incidenciaObject.getString("carretera"));
        incidencia.setFechaInicio(incidenciaObject.getString("fechaInicio"));

        // Parsear Ciudad
        JSONObject ciudadObject = incidenciaObject.getJSONObject("ciudad");
        Ciudad ciudad = new Ciudad();
        ciudad.setId(ciudadObject.getInt("id"));
        ciudad.setNombre(ciudadObject.getString("nombre"));
        ciudad.setLatitud(ciudadObject.getString("latitud"));
        ciudad.setLongitud(ciudadObject.getString("longitud"));
        incidencia.setCiudad(ciudad);

        // Parsear Provincia
        JSONObject provinciaObject = incidenciaObject.getJSONObject("provincia");
        Provincia provincia = new Provincia();
        provincia.setId(provinciaObject.getInt("id"));
        provincia.setNombre(provinciaObject.getString("nombre"));
        provincia.setLatitud(provinciaObject.getString("latitud"));
        provincia.setLongitud(provinciaObject.getString("longitud"));
        incidencia.setProvincia(provincia);

        // Parsear Región
        JSONObject regionObject = incidenciaObject.getJSONObject("region");
        Region region = new Region();
        region.setId(regionObject.getInt("id"));
        region.setIdRegion(regionObject.getInt("idRegion"));
        region.setNombreEs(regionObject.getString("nombreEs"));
        region.setNombreEu(regionObject.getString("nombreEu"));
        incidencia.setRegion(region);

        // Parsear TipoIncidencia
        JSONObject tipoIncidenciaObject = incidenciaObject.getJSONObject("tipoIncidencia");
        TipoIncidencia tipoIncidencia = new TipoIncidencia();
        tipoIncidencia.setId(tipoIncidenciaObject.getInt("id"));
        tipoIncidencia.setNombre(tipoIncidenciaObject.getString("nombre"));
        incidencia.setTipoIncidencia(tipoIncidencia);

        return incidencia;
    }


    public static class ObtenerTodasLasIncidencias extends AsyncTask<Void, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Incidencia> incidencias = new ArrayList<>();

            try {
                // URL de las incidencias
                URL url = new URL("http://10.10.13.251:8080/incidencias");
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
                return null; // Si ocurre un error, retornar null
            } finally {
                // Asegurarse de cerrar el BufferedReader y la conexión
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
                    JSONObject incidenciaObject = jsonArray.getJSONObject(i);
                    Incidencia incidencia = parseIncidencia(incidenciaObject);
                    incidencias.add(incidencia);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return incidencias; // Retorna la lista de incidencias
        }

        @Override
        protected void onPostExecute(List<Incidencia> incidencias) {
            if (incidencias != null) {
                for (Incidencia incidencia : incidencias) {
                    Log.d("Incidencia", incidencia.toString());
                }
            }
        }


    }

    public static class ObtenerIncidenciasRegion extends AsyncTask<Void, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Incidencia> incidencias = new ArrayList<>();

            try {

                URL url = new URL("http://10.10.13.251:8080/filtrosIncidencias/region?idRegion=1");
                urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setRequestMethod("POST");


                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                // Crear el JSON con los parámetros que necesitamos (por ejemplo, idRegion)
//                JSONObject jsonParam = new JSONObject();
//                jsonParam.put("idRegion", 1);  // Ejemplo de cómo se enviaría el parámetro "idRegion"


                DataOutputStream writer = new DataOutputStream(urlConnection.getOutputStream());
//                writer.writeBytes(jsonParam.toString());
                writer.flush();
                writer.close();

                // Verificar código de respuesta
                int code = urlConnection.getResponseCode();
                if (code != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Respuesta inválida del servidor: " + code);
                }


                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null; // Si ocurre un error, retornar null
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

            // Parsear el JSON de la respuesta
            try {
                JSONArray jsonArray = new JSONArray(jsonResult.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject incidenciaObject = jsonArray.getJSONObject(i);
                    Incidencia incidencia = parseIncidencia(incidenciaObject);
                    incidencias.add(incidencia);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return incidencias;
        }

            @Override
        protected void onPostExecute(List<Incidencia> incidencias) {
            if (incidencias != null) {
                for (Incidencia incidencia : incidencias) {
                    Log.d("Incidencia", incidencia.toString());
                }
            }
        }


    }
}
