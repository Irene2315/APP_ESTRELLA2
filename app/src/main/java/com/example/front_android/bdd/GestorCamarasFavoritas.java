package com.example.front_android.bdd;

import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.front_android.Modelos.FavoritoCamara;

import java.util.ArrayList;

public class GestorCamarasFavoritas {
    public static final String TABLA_FAVORITOS_CAMARAS = "favoritosCamaras";
    public static final String ID_PRY_CAM = "id";
    public static final String ID_CAMARA = "id_camara";

    public static final String TABLA_FAVORITOS_CAMARA_CREATE =
            "CREATE TABLE " + TABLA_FAVORITOS_CAMARAS + " (" +
                    ID_PRY_CAM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ID_CAMARA + " TEXT);";

    private SQLiteDatabase bbdd_favoritos;

    public GestorCamarasFavoritas(SQLiteDatabase bbdd) {
        this.bbdd_favoritos = bbdd;
    }

    public void insertarFavoritosCamaras(String id_cam) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_CAMARA, id_cam);

        this.bbdd_favoritos.insert(TABLA_FAVORITOS_CAMARAS, null, contentValues);
        Log.d("insercionCamaras", "Correcta");
    }

    public void eliminarFavoritosCamaras(String id_cam) {
        int rowsDeleted = this.bbdd_favoritos.delete(
                TABLA_FAVORITOS_CAMARAS,
                ID_CAMARA + " = ?",
                new String[]{id_cam}
        );

        if (rowsDeleted > 0) {
            Log.d("eliminaciónCamara", "Cámara eliminada correctamente");
        } else {
            Log.d("eliminaciónCamara", "No se encontró la cámara para eliminar");
        }
    }

    public ArrayList<FavoritoCamara> seleccionarTodasLasCamarasFavoritas() {
        ArrayList<FavoritoCamara> camarasFav = new ArrayList<>();
        String query = "SELECT * FROM " + TABLA_FAVORITOS_CAMARAS;
        Cursor cursor = bbdd_favoritos.rawQuery(query, null);
        FavoritoCamara favoritoCam;
        while (cursor.moveToNext()) {
            favoritoCam = new FavoritoCamara();
            favoritoCam.setId(cursor.getInt(0));
            favoritoCam.setIdCamara(cursor.getInt(1));

            camarasFav.add(favoritoCam);
        }
        cursor.close();
        return camarasFav;
    }
}
