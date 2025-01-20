package com.example.front_android.Modelos;

import java.io.Serializable;

public class Incidencia implements Serializable {
    private int id;
    private String latitud;
    private String longitud;
    private String causa;
    private String nivelIncidencia;
    private String carretera;
    private String fechaInicio;

    private Region region;
    private Ciudad ciudad;
    private TipoIncidencia tipoIncidencia;
    private Provincia provincia;


    public Incidencia() {
    }

    public Incidencia(int id, String latitud, String longitud, String causa, String nivelIncidencia, String carretera, String fechaInicio, Region region, Ciudad ciudad, TipoIncidencia tipoIncidencia, Provincia provincia) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.causa = causa;
        this.nivelIncidencia = nivelIncidencia;
        this.carretera = carretera;
        this.fechaInicio = fechaInicio;
        this.region = region;
        this.ciudad = ciudad;
        this.tipoIncidencia = tipoIncidencia;
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

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getNivelIncidencia() {
        return nivelIncidencia;
    }

    public void setNivelIncidencia(String nivelIncidencia) {
        this.nivelIncidencia = nivelIncidencia;
    }

    public String getCarretera() {
        return carretera;
    }

    public void setCarretera(String carretera) {
        this.carretera = carretera;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public TipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(TipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "id=" + id +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", causa='" + causa + '\'' +
                ", nivelIncidencia='" + nivelIncidencia + '\'' +
                ", carretera='" + carretera + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", region=" + region +
                ", ciudad=" + ciudad +
                ", tipoIncidencia=" + tipoIncidencia +
                ", provincia=" + provincia +
                '}';
    }
}
