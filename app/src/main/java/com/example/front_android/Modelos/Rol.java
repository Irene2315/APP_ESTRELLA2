package com.example.front_android.Modelos;

import java.io.Serializable;

public class Rol implements Serializable {
    private int id;
    private String nombre;

    public Rol(){

    }

    public Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
