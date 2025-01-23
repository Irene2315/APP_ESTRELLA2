package com.example.front_android.PETICIONES_API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.front_android.Modelos.Rol;
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
                URL url = new URL("http://10.10.13.251:8080/usuarios");
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
                        JSONObject rolObject = userObject.getJSONObject("rol");

                        Rol rol = new Rol();
                        rol.setId(rolObject.getInt("id"));
                        rol.setNombre(rolObject.getString("nombre"));

                        usuario.setNombre(nombre);
                        usuario.setContrasena(contrasena);
                        usuario.setCorreoElectronico(correo);
                        usuario.setRol(rol);

                        Log.d("ObtenerUsuarios", "Usuario: Nombre = " + nombre +
                                ", Contraseña = " + contrasena + ", Correo = " + correo + ", Rol = " + rol);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class LoguearUsuario extends AsyncTask<String, Void, String> {
        private Context context;

        public LoguearUsuario(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
                URL url = new URL("http://10.10.13.251:8080/login");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                String nombre = strings[0];
                String contraseña = strings[1];

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("nombre", nombre);
                jsonParam.put("contraseña", contraseña);

                Log.d("LoguearUsuario", "JSON enviado: " + jsonParam.toString());

                try (DataOutputStream writer = new DataOutputStream(urlConnection.getOutputStream())) {
                    writer.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
                    writer.flush();
                }

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResult.append(line);
                    }
                } else {
                    Log.e("LoguearUsuario", "Error: Código de respuesta " + responseCode);
                    return null;
                }

            } catch (Exception e) {
                Log.e("LoguearUsuario", "Error en la conexión: " + e.getMessage(), e);
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
                    JSONObject responseJson = new JSONObject(result);

                    String rol = responseJson.optString("rol");

                    if (rol != null && !rol.isEmpty()) {
                        Toast.makeText(context, rol, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Log.e("LoguearUsuario", "Error al parsear la respuesta JSON: " + e.getMessage());
                    Toast.makeText(context, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public static class RegistrarUsuario extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder jsonResult = new StringBuilder();

            try {
                URL url = new URL("http://10.10.13.251:8080/registroAndroid");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setDoOutput(true);

                String nombre = strings[0];
                String contrasena = strings[1];
                String correo = strings[2];


                JSONObject jsonParam = new JSONObject();
                jsonParam.put("nombre", nombre);
                jsonParam.put("contraseña", contrasena);
                jsonParam.put("correo", correo);


                Log.d("RegistrarUsuario", "JSON enviado: " + jsonParam.toString());

                try (DataOutputStream writer = new DataOutputStream(urlConnection.getOutputStream())) {
                    writer.write(jsonParam.toString().getBytes(StandardCharsets.UTF_8));
                    writer.flush();
                }

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonResult.append(line);
                    }
                } else {
                    Log.e("RegistrarUsuario", "Error: Código de respuesta " + responseCode);
                    return null;
                }
            } catch (Exception e) {
                Log.e("RegistrarUsuario", "Error en la conexión: " + e.getMessage(), e);
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
                Log.d("RegistrarUsuario", "Respuesta del servidor: " + result);
            } else {
                Log.e("RegistrarUsuario", "No se recibió respuesta del servidor.");
            }
        }
    }
}
