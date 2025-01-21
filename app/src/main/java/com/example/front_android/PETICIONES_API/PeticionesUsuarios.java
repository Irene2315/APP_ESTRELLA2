package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Rol;
import com.example.front_android.Modelos.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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



}
