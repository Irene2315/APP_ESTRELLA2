package com.example.front_android.bdd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GestorBDD {

    private ConectorBDD conexion;
    private SQLiteDatabase bbdd_favoritos;

    public GestorBDD(Context context) {
        conexion = new ConectorBDD(context);
    }

    public GestorBDD conectar() throws SQLException {
        bbdd_favoritos = conexion.getWritableDatabase();
        return this;
    }

    public void desconectar() {
        conexion.close();
    }

    public GestorCamarasFavoritas getGestorCamarasFavoritas() {
        return new GestorCamarasFavoritas(bbdd_favoritos);
    }

    public GestorIncidenciasFavoritas getGestorIncidenciasFavoritas() {
        return new GestorIncidenciasFavoritas(bbdd_favoritos);
    }
}
