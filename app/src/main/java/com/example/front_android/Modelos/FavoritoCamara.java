package com.example.front_android.Modelos;

import java.io.Serializable;

public class FavoritoCamara implements Serializable {

    private int id;
    private int idCamara;


    /**
     * Constructor vacío
     */
    public FavoritoCamara() {
    }

    /**
     * Constructor con parámetros para inicializar todos los atributos
     * @param id Identificador de la cámara favorita.
     * @param idCamara Identificador de la cámara.
     */
    public FavoritoCamara(int id, int idCamara) {
        this.id = id;
        this.idCamara = idCamara;
    }

    // Getters y Setters para acceder y modificar los atributos de cámaras favoritas.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCamara() {
        return idCamara;
    }

    public void setIdCamara(int idCamara) {
        this.idCamara = idCamara;
    }
}
