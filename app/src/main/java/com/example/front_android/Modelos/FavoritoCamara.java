package com.example.front_android.Modelos;

import java.io.Serializable;

public class FavoritoCamara implements Serializable {

    private int id;
    private int idCamara;

    public FavoritoCamara() {
    }

    public FavoritoCamara(int id, int idCamara) {
        this.id = id;
        this.idCamara = idCamara;
    }

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
