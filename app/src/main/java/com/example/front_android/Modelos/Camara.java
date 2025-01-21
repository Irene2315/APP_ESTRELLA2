package com.example.front_android.Modelos;

import java.io.Serializable;

public class Camara implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;
    private String urlImagen;
    private Region region;

    public Camara() {
    }

    public Camara(int id, String nombre, String latitud, String longitud, String urlImagen, Region region) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.urlImagen = urlImagen;
        this.region = region;
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

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Camara{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", urlImagen='" + urlImagen + '\'' +
                ", region=" + region +
                '}';
    }
}
