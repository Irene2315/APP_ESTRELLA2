package com.example.front_android.PETICIONES_API;

import android.os.AsyncTask;
import android.util.Log;

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


}
