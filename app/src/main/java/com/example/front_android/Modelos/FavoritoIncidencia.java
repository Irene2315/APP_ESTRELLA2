package com.example.front_android.Modelos;

import java.io.Serializable;

public class FavoritoIncidencia implements Serializable {

    private int id;
    private int idIncidencia;

    /**
     * Constructor vacío
     */
    public FavoritoIncidencia() {
    }

    /**
     * Constructor con parámetros para inicializar todos los atributos
     * @param id Identificador de la incidencia favorita.
     * @param idIncidencia Identificador de la incidencia.
     */
    public FavoritoIncidencia(int id, int idIncidencia) {
        this.id = id;
        this.idIncidencia = idIncidencia;
    }

    // Getters y Setters para acceder y modificar los atributos de incidencias favoritas.

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
