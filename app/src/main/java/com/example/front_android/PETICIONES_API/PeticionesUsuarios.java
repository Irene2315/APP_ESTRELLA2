package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class PeticionesUsuarios {


    public static class ObtenerUsuario extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
//                https://jsonplaceholder.typicode.com/users
//                android:usesCleartextTraffic="true"
                //10.0.2.2
                URL url = new URL("http:/10.10.13.251:8080/usuarios");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();
                if (code != 200) {
                    throw new IOException("Invalid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return jsonResult.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);



                    for (int i = 0; i < jsonArray.length(); i++) {
                        Usuario usuario = new Usuario();
                        JSONObject userObject = jsonArray.getJSONObject(i);

                        String nombre = userObject.getString("nombre");
                        String contrasena = userObject.getString("contraseña");
                        String correo = userObject.getString("correo");
                        String rol = userObject.getString("rol");


                        usuario.setNombre(nombre);
                        usuario.setContrasena(contrasena);
                        usuario.setCorreoElectronico(correo);
                        usuario.setRol(rol);



                        Log.d("ObtenerUsuarios", "Usuario: Nombre = " + nombre +
                                ", Usuario = " + contrasena + ", Correo = " + correo +  ", Rol = " + rol);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class LoguearUsuario extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
                // URL para el login
                URL url = new URL("http://10.10.13.251:8080/login"); urlConnection = (HttpURLConnection) url.openConnection();
                // Configuración de la conexión
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json"); urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);


                JSONObject jsonParam = new JSONObject();
                //jsonParam.put("correoElectronico", params[0]); // Parámetro 1: correo
                //jsonParam.put("contrasena", params[1]);        // Parámetro 2: contraseña
                jsonParam.put("nombre", "mijael"); // Parámetro 1: nombre
                jsonParam.put("contraseña", "admin"); // Parámetro 2: contraseña



                DataOutputStream writer = new DataOutputStream(urlConnection.getOutputStream());
                writer.writeBytes(jsonParam.toString());
                writer.flush();
                writer.close();


                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResult.append(line).append("\n");
                    }
                } else {
                    Log.e("LoguearUsuario", "Error en la respuesta del servidor: Código " + responseCode);
                    return null;
                }
            } catch (Exception e) {
                Log.e("LoguearUsuario", "Error en la conexión: " + e.getMessage());
                e.printStackTrace();
                return null;
            } finally {

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("LoguearUsuario", "Error al cerrar el reader: " + e.getMessage());
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return jsonResult.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {

                    JSONObject responseJson = new JSONObject(result);
                    String rol = responseJson.optString("rol", "No se recibió rol");
                    Log.d("LoguearUsuario", "Rol recibido: " + rol);
                } catch (JSONException e) {
                    Log.e("LoguearUsuario", "Error al parsear la respuesta JSON: " + e.getMessage());
                }
            } else {
                Log.e("LoguearUsuario", "No se recibió respuesta del servidor.");
            }
        }
    }

}





