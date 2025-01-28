package com.example.front_android.PETICIONES_API;


import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.MapaFragment;
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
import java.util.concurrent.Executor;

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

        // Parsear Ciudad (verificar si no es null)
        if (!incidenciaObject.isNull("ciudad")) {
            JSONObject ciudadObject = incidenciaObject.getJSONObject("ciudad");
            Ciudad ciudad = new Ciudad();
            ciudad.setId(ciudadObject.getInt("id"));
            ciudad.setNombre(ciudadObject.getString("nombre"));
            ciudad.setLatitud(ciudadObject.getString("latitud"));
            ciudad.setLongitud(ciudadObject.getString("longitud"));
            incidencia.setCiudad(ciudad);
        } else {
            Log.d("parseIncidencia", "Campo 'ciudad' es null");
            incidencia.setCiudad(null); // Si no existe, asignar null
        }

        // Parsear Provincia
        if (!incidenciaObject.isNull("provincia")) {
            JSONObject provinciaObject = incidenciaObject.getJSONObject("provincia");
            Provincia provincia = new Provincia();
            provincia.setId(provinciaObject.getInt("id"));
            provincia.setNombre(provinciaObject.getString("nombre"));
            provincia.setLatitud(provinciaObject.getString("latitud"));
            provincia.setLongitud(provinciaObject.getString("longitud"));
            incidencia.setProvincia(provincia);
        } else {
            Log.d("parseIncidencia", "Campo 'provincia' es null");
            incidencia.setProvincia(null);
        }

        // Parsear Región
        if (!incidenciaObject.isNull("region")) {
            JSONObject regionObject = incidenciaObject.getJSONObject("region");
            Region region = new Region();
            region.setId(regionObject.getInt("id"));
            region.setIdRegion(regionObject.getInt("idRegion"));
            region.setNombreEs(regionObject.getString("nombreEs"));
            region.setNombreEu(regionObject.getString("nombreEu"));
            incidencia.setRegion(region);
        } else {
            Log.d("parseIncidencia", "Campo 'region' es null");
            incidencia.setRegion(null);
        }

        // Parsear TipoIncidencia
        if (!incidenciaObject.isNull("tipoIncidencia")) {
            JSONObject tipoIncidenciaObject = incidenciaObject.getJSONObject("tipoIncidencia");
            TipoIncidencia tipoIncidencia = new TipoIncidencia();
            tipoIncidencia.setId(tipoIncidenciaObject.getInt("id"));
            tipoIncidencia.setNombre(tipoIncidenciaObject.getString("nombre"));
            incidencia.setTipoIncidencia(tipoIncidencia);
        } else {
            Log.d("parseIncidencia", "Campo 'tipoIncidencia' es null");
            incidencia.setTipoIncidencia(null);
        }

        return incidencia;
    }



    public static class ObtenerTodasLasIncidencias extends AsyncTask<Void, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Void... params) {
            Log.d("doInBackground", "Iniciando tarea asíncrona");
            List<Incidencia> incidencias = new ArrayList<>();
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
                URL url = new URL("http://10.10.13.251:8080/incidencias");
                urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Respuesta inválida del servidor: " + urlConnection.getResponseCode());
                }

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(jsonResult.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject incidenciaObject = jsonArray.getJSONObject(i);
                    Incidencia incidencia = parseIncidencia(incidenciaObject);
                    incidencias.add(incidencia);
                }

            } catch (Exception e) {
                Log.e("doInBackground", "Error al procesar la solicitud", e);
                return null;
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (urlConnection != null) urlConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return incidencias;
        }

    }

    public static class ObtenerIncidenciasRegion extends AsyncTask<Integer, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Integer... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Incidencia> incidencias = new ArrayList<>();

            try {
                // Validación del parámetro
                if (params == null || params.length == 0) {
                    throw new IllegalArgumentException("No se proporcionó un ID de región válido.");
                }

                int regionId = params[0];
                Log.d("ObtenerIncidenciasRegion", "región ID: " + regionId);

                URL url = new URL("http://10.10.13.251:8080/filtrosIncidencias/region?idRegion=" + regionId);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

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
    }


    public static class ObtenerIncidenciasProvincia extends AsyncTask<Integer, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Integer... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Incidencia> incidencias = new ArrayList<>();

            try {
                int provinciaId = params[0];
                URL url = new URL("http://10.10.13.251:8080/filtrosIncidencias/provincia?idProvincia=" + provinciaId);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

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
    }

    public static class ObtenerIncidenciasCiudad extends AsyncTask<Integer, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Integer... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Incidencia> incidencias = new ArrayList<>();

            try {
                int ciudadId = params[0];
                Log.d("ObtenerIncidenciasCiudad", "Ciudad ID: " + ciudadId);

                URL url = new URL("http://10.10.13.251:8080/filtrosIncidencias/ciudad?idCiudad=" + ciudadId);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                int code = urlConnection.getResponseCode();
                Log.d("ObtenerIncidenciasCiudad", "Código de respuesta HTTP: " + code);

                if (code != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Respuesta inválida del servidor: " + code);
                }

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }

                Log.d("ObtenerIncidenciasCiudad", "Respuesta JSON: " + jsonResult.toString());

                // Parse JSON
                JSONArray jsonArray = new JSONArray(jsonResult.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject incidenciaObject = jsonArray.getJSONObject(i);
                    Incidencia incidencia = parseIncidencia(incidenciaObject);
                    incidencias.add(incidencia);
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e("ObtenerIncidenciasCiudad", "Error en doInBackground: " + e.getMessage());
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
            return incidencias;
        }
    }


    public static class ObtenerIncidenciasTipoIncidencia extends AsyncTask<Integer, Void, List<Incidencia>> {

        @Override
        protected List<Incidencia> doInBackground(Integer... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Incidencia> incidencias = new ArrayList<>();

            try {
                int tipoId = params[0];

                URL url = new URL("http://10.10.13.251:8080/filtrosIncidencias/tipoIncidencia?idTipoIncidencia=" + tipoId);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                int code = urlConnection.getResponseCode();

                if (code != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Respuesta inválida del servidor: " + code);
                }

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }

                JSONArray jsonArray = new JSONArray(jsonResult.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject incidenciaObject = jsonArray.getJSONObject(i);
                    Incidencia incidencia = parseIncidencia(incidenciaObject);
                    incidencias.add(incidencia);
                }

            } catch (IOException | JSONException e) {
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
            return incidencias;
        }
    }
}