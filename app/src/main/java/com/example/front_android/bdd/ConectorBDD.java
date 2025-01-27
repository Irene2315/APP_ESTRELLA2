package com.example.front_android.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConectorBDD extends SQLiteOpenHelper {


    private static final String nombre_DB="bbdd_favoritos.db";
    private static final int version_DB = 1;

    //Conexion
    public ConectorBDD(@Nullable Context context) {
        super(context, nombre_DB, null, version_DB);
    }

    //Crear solo la primera vez
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(GestorCamarasFavoritas.TABLA_FAVORITOS_CAMARA_CREATE);
        sqLiteDatabase.execSQL(GestorIncidenciasFavoritas.TABLA_FAVORITOS_INCIDENCIAS_CREATE);
    }

    //Actualizar
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+GestorCamarasFavoritas.TABLA_FAVORITOS_CAMARAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+GestorIncidenciasFavoritas.TABLA_FAVORITOS_INCIDENCIAS);
        onCreate(sqLiteDatabase);

    }
}