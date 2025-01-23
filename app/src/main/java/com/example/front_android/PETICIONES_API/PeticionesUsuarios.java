package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Rol;
import com.example.front_android.Modelos.Usuario;

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

    /**
     * Método para parsear un objeto JSON a un objeto Usuario.
     */
    private static Usuario parseUsuario(JSONObject usuarioObject) throws JSONException {
        Usuario usuario = new Usuario();

        usuario.setId(usuarioObject.getInt("id"));
        usuario.setNombre(usuarioObject.getString("nombre"));
        usuario.setCorreoElectronico(usuarioObject.getString("correoElectronico"));
        usuario.setContrasena(usuarioObject.optString("contrasena", null)); // Manejo opcional de la contraseña

        // Parsear el rol del usuario
        JSONObject rolObject = usuarioObject.getJSONObject("rol");
        Rol rol = new Rol();
        rol.setId(rolObject.getInt("id"));
        rol.setNombre(rolObject.getString("nombre"));
        usuario.setRol(rol);

        return usuario;
    }

    /**
     * Clase AsyncTask para obtener un usuario desde el servidor.
     */
    public static class ObtenerUnUsuario extends AsyncTask<Void, Void, Usuario> {

        @Override
        protected Usuario doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
                // URL para obtener el usuario
                URL url = new URL("http://10.10.13.251:8080/usuarios/1");
                urlConnection = (HttpURLConnection) url.openConnection();

                // Verificar el código de respuesta del servidor
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
                Log.e("Error", "Error de conexión: " + e.getMessage());
                e.printStackTrace();
                return null;
            } finally {
                // Cerrar recursos
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("Error", "Error al cerrar el reader: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            // Parsear la respuesta JSON a un objeto Usuario
            try {
                JSONObject usuarioObject = new JSONObject(jsonResult.toString());
                return parseUsuario(usuarioObject);
            } catch (JSONException e) {
                Log.e("Error", "Error al parsear el JSON: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            if (usuario != null) {
                Log.d("Usuario", usuario.toString());
            } else {
                Log.e("Usuario", "No se pudo obtener el usuario.");
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





