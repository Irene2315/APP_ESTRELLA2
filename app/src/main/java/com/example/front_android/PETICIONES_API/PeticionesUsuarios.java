package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.front_android.Modelos.Rol;
import com.example.front_android.Modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PeticionesUsuarios {

    public static class ObtenerUsuario extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
                URL url = new URL("http://localhost:8080/usuario");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int code = urlConnection.getResponseCode();
                Log.d("ObtenerUsuarios", "Código de respuesta: " + code);

                if (code != 200) {
                    throw new IOException("Respuesta inválida del servidor: " + code);
                }


                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    jsonResult.append(line).append("\n");
                }
                Log.d("ObtenerUsuarios", "Respuesta JSON: " + jsonResult.toString());
            } catch (Exception e) {
                Log.e("ObtenerUsuarios", "Error en la conexión: " + e.getMessage(), e);
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
            if (result != null && !result.isEmpty()) {
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Usuario usuario = new Usuario();
                        JSONObject userObject = jsonArray.getJSONObject(i);

                        String nombre = userObject.getString("nombre");
                        String contrasena = userObject.getString("contraseña");
                        String correo = userObject.getString("correo");

                        JSONObject rolObject = userObject.getJSONObject("rol");
                        Rol rol = new Rol();
                        rol.setId(rolObject.getInt("id"));
                        rol.setNombre(rolObject.getString("nombre"));

                        usuario.setNombre(nombre);
                        usuario.setContrasena(contrasena);
                        usuario.setCorreoElectronico(correo);
                        usuario.setRol(rol);

                    }

                } catch (JSONException e) {
                    Log.e("ObtenerUsuarios", "Error procesando el JSON: " + e.getMessage());
                }
            }
        }
    }
}
