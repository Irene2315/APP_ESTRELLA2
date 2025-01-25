package com.example.front_android.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.front_android.Modelos.FavoritoIncidencia;

import java.util.ArrayList;

public class GestorIncidenciasFavoritas {
    public static final String TABLA_FAVORITOS_INCIDENCIAS = "favoritosIncidencias";
    public static final String ID_PRY_IN = "id";
    public static final String ID_INCIDENCIA = "id_incidencia";

    public static final String TABLA_FAVORITOS_INCIDENCIAS_CREATE =
            "CREATE TABLE " + TABLA_FAVORITOS_INCIDENCIAS + " (" +
                    ID_PRY_IN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ID_INCIDENCIA + " TEXT);";

    private SQLiteDatabase bbdd_favoritos;

    public GestorIncidenciasFavoritas(SQLiteDatabase bbdd) {
        this.bbdd_favoritos = bbdd;
    }


    public void insertarFavoritosIncidencias(String id_inc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_INCIDENCIA, id_inc);

        this.bbdd_favoritos.insert(TABLA_FAVORITOS_INCIDENCIAS, null, contentValues);
        Log.d("insercionIncidencias", "Correcta");
    }

    public void eliminarFavoritosIncidencias(String id_inc) {
        int rowsDeleted = this.bbdd_favoritos.delete(
                TABLA_FAVORITOS_INCIDENCIAS,
                ID_INCIDENCIA + " = ?",
                new String[]{id_inc}
        );

        if (rowsDeleted > 0) {
            Log.d("eliminaciónIncidencia", "Incidencia eliminada correctamente");
        } else {
            Log.d("eliminaciónIncidencia", "No se encontró la incidencia para eliminar");
        }
    }

    public ArrayList<FavoritoIncidencia> seleccionarTodasLasIncidenciasFavoritas() {
        ArrayList<FavoritoIncidencia> incidenciasFav = new ArrayList<>();
        String query = "SELECT * FROM " + TABLA_FAVORITOS_INCIDENCIAS;
        Cursor cursor = bbdd_favoritos.rawQuery(query, null);
        FavoritoIncidencia favoritoInc;
        while (cursor.moveToNext()) {
            favoritoInc = new FavoritoIncidencia();
            favoritoInc.setId(cursor.getInt(0));
            favoritoInc.setIdIncidencia(cursor.getInt(1));

            incidenciasFav.add(favoritoInc);
        }
        cursor.close();
        return incidenciasFav;
    }
}
