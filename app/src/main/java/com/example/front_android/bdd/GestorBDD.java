package com.example.front_android.bdd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Clase GestorBDD para manejar la conexión con la base de datos SQLite.
 */
public class GestorBDD {

    // Instancia del conector a la base de datos
    private ConectorBDD conexion;
    private SQLiteDatabase bbdd_favoritos;

    /**
     * Constructor que inicializa la conexión con la base de datos.
     */
    public GestorBDD(Context context) {
        conexion = new ConectorBDD(context);
    }

    /**
     * Método para conectar y obtener una base de datos escribible.
     */
    public GestorBDD conectar() throws SQLException {
        bbdd_favoritos = conexion.getWritableDatabase();
        return this;
    }

    /**
     * Método para cerrar la conexión con la base de datos.
     */
    public void desconectar() {
        conexion.close();
    }

    /**
     * Método para obtener el gestor de cámaras favoritas.
     * @return Una instancia de GestorCamarasFavoritas asociada a la base de datos.
     */
    public GestorCamarasFavoritas getGestorCamarasFavoritas() {
        return new GestorCamarasFavoritas(bbdd_favoritos);
    }

    /**
     * Método para obtener el gestor de incidencias favoritas.
     * @return Una instancia de GestorIncidenciasFavoritas asociada a la base de datos.
     */
    public GestorIncidenciasFavoritas getGestorIncidenciasFavoritas() {
        return new GestorIncidenciasFavoritas(bbdd_favoritos);
    }
}
