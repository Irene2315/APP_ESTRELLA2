package com.example.front_android.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Clase ConectorBDD que extiende SQLiteOpenHelper para gestionar la creación y actualización
 * de la base de datos SQLite.
 */
public class ConectorBDD extends SQLiteOpenHelper {

    // Nombre del archivo de la base de datos
    private static final String nombre_DB = "bbdd_favoritos.db";

    private static final int version_DB = 1;

    /**
     * Constructor de la clase que inicializa la conexión con la base de datos.
     */
    public ConectorBDD(@Nullable Context context) {
        super(context, nombre_DB, null, version_DB);
    }

    /**
     * Método que se ejecuta solo la primera vez para crear la base de datos.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creación de la tabla para almacenar cámaras favoritas
        sqLiteDatabase.execSQL(GestorCamarasFavoritas.TABLA_FAVORITOS_CAMARA_CREATE);

        // Creación de la tabla para almacenar incidencias favoritas
        sqLiteDatabase.execSQL(GestorIncidenciasFavoritas.TABLA_FAVORITOS_INCIDENCIAS_CREATE);
    }

    /**
     * Método que se ejecuta cuando hay una actualización en la versión de la base de datos.
     * Se eliminan las tablas existentes y se vuelven a crear con la nueva estructura.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Eliminar las tablas si existen antes de recrearlas
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GestorCamarasFavoritas.TABLA_FAVORITOS_CAMARAS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GestorIncidenciasFavoritas.TABLA_FAVORITOS_INCIDENCIAS);

        // Volver a crear las tablas con la estructura actualizada
        onCreate(sqLiteDatabase);
    }
}
