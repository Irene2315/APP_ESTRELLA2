package com.example.front_android.Modelos;

import java.io.Serializable;

public class Ciudad implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;
    private Provincia provincia;

    public Ciudad() {
    }

    public Ciudad(int id, String latitud, String longitud, String nombre, Provincia provincia) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.provincia = provincia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
