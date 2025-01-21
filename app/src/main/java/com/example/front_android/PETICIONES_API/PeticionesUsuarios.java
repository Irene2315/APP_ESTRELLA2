package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

import com.example.front_android.Modelos.Incidencia;
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
import java.util.ArrayList;
import java.util.List;

public class PeticionesUsuarios {

    public static Usuario parseUsuario(JSONObject usuarioObject) throws JSONException{
        Usuario usuario = new Usuario();

        usuario.setId(usuarioObject.getInt("id"));
        usuario.setNombre(usuarioObject.getString("nombre"));
        usuario.setCorreoElectronico(usuarioObject.getString("correo"));
        usuario.setContrasena(usuarioObject.getString("contraseña"));

        JSONObject rolObject = usuarioObject.getJSONObject("rol");
        Rol rol = new Rol();
        rol.setId(rolObject.getInt("id"));
        rol.setNombre(rolObject.getString("nombre"));


        return usuario;
    }

    public static class ObtenerUsuario extends AsyncTask<Void, Void, List<Usuario>> {

        @Override
        protected List<Usuario> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();
            List<Usuario> usuarios = new ArrayList<>();

            try {
                URL url = new URL("http://localhost:8080/usuarios");
                urlConnection = (HttpURLConnection) url.openConnection();

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
            try {
                JSONArray jsonArray = new JSONArray(jsonResult.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject usuarioObject = jsonArray.getJSONObject(i);
                    Usuario in = parseUsuario(usuarioObject);
                    usuarios.add(in);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return usuarios;
        }

        @Override
        protected void onPostExecute(List<Usuario> usuarios) {
            if (usuarios != null){
                for (Usuario u : usuarios){
                    Log.d("Usuarios", u.toString());
                }
            }
        }
    }
}
