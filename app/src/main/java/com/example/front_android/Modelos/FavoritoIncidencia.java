package com.example.front_android.Modelos;

import java.io.Serializable;

public class FavoritoIncidencia implements Serializable {

    private int id;
    private int idIncidencia;

    public FavoritoIncidencia() {
    }

    public FavoritoIncidencia(int id, int idIncidencia) {
        this.id = id;
        this.idIncidencia = idIncidencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }
}
