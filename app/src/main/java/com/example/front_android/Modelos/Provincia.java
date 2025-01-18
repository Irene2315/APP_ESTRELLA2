package com.example.front_android.Modelos;


import java.io.Serializable;
import java.util.List;

public class Provincia implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;

    private List<Ciudad> ciudades;

    public Provincia() {
    }

    public Provincia(List<Ciudad> ciudades, int id, String latitud, String longitud, String nombre) {
        this.ciudades = ciudades;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
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
}
