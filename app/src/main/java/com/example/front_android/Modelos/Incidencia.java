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
    private int imagen;

    private Region region;
    private Ciudad ciudad;
    private TipoIncidencia tipoIncidencia;
    private Provincia provincia;

    /**
     * Constructor vacío
     */
    public Incidencia() {
    }

    /**
     * Constructor sin imagen.
     * @param id Identificador de la incidencia.
     * @param latitud Latitud donde ocurre la incidencia.
     * @param longitud Longitud donde ocurre la incidencia.
     * @param causa Causa de la incidencia.
     * @param nivelIncidencia Nivel de la incidencia.
     * @param carretera Nombre de la carretera donde ocurre la incidencia.
     * @param fechaInicio Fecha de inicio de la incidencia.
     * @param region Región donde ocurre la incidencia.
     * @param ciudad Ciudad donde ocurre la incidencia.
     * @param tipoIncidencia Tipo de incidencia.
     * @param provincia Provincia donde ocurre la incidencia.
     */
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

    /**
     * Constructor con imagen.
     * @param id Identificador de la incidencia.
     * @param latitud Latitud donde ocurre la incidencia.
     * @param longitud Longitud donde ocurre la incidencia.
     * @param causa Causa de la incidencia.
     * @param nivelIncidencia Nivel de la incidencia.
     * @param carretera Nombre de la carretera donde ocurre la incidencia.
     * @param fechaInicio Fecha de inicio de la incidencia.
     * @param imagen Identificador de la imagen asociada a la incidencia.
     * @param region Región donde ocurre la incidencia.
     * @param ciudad Ciudad donde ocurre la incidencia.
     * @param tipoIncidencia Tipo de incidencia.
     * @param provincia Provincia donde ocurre la incidencia.
     */
    public Incidencia(int id, String latitud, String longitud, String causa, String nivelIncidencia, String carretera, String fechaInicio, int imagen, Region region, Ciudad ciudad, TipoIncidencia tipoIncidencia, Provincia provincia) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.causa = causa;
        this.nivelIncidencia = nivelIncidencia;
        this.carretera = carretera;
        this.fechaInicio = fechaInicio;
        this.imagen = imagen;
        this.region = region;
        this.ciudad = ciudad;
        this.tipoIncidencia = tipoIncidencia;
        this.provincia = provincia;
    }

    // Getters y Setters para acceder y modificar los atributos de la incidencia.

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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    /**
     * Método que muestra los datos de la Incidencia.
     */
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