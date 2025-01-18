package com.example.front_android.Modelos;

import java.io.Serializable;

public class Camara implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;
    private String carretera;
    private String kilometro;
    private String direccion;
    private String urlImagen;
    private Region region;

    public Camara() {
    }

    public Camara(int id, String nombre, String latitud, String longitud, String carretera, String kilometro, String direccion, String urlImagen, Region region) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.carretera = carretera;
        this.kilometro = kilometro;
        this.direccion = direccion;
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

    public void setLogitud(String longitud) {
        this.longitud = longitud;
    }

    public String getCarretera() {
        return carretera;
    }

    public void setCarretera(String carretera) {
        this.carretera = carretera;
    }

    public String getKilometro() {
        return kilometro;
    }

    public void setKilometro(String kilometro) {
        this.kilometro = kilometro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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
}
