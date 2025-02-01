package com.example.front_android.Modelos;

import java.io.Serializable;

public class Camara implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;
    private String urlImagen;
    private int imagen;
    private Region region;

    /**
     * Constructor vacío de la Camara.
     */
    public Camara() {
    }

    /**
     * Constructor para el modelo Camara
     */
    public Camara(int id, String nombre, String latitud, String longitud, String urlImagen, Region region) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.urlImagen = urlImagen;
        this.region = region;
    }

    /**
     * Constructor con parámetros para inicializar todos los atributos de la Camara
     * @param id Identificador de la cámara.
     * @param nombre Nombre de la cámara.
     * @param latitud Latitud de la ubicación de la cámara.
     * @param longitud Longitud de la ubicación de la cámara.
     * @param urlImagen URL de la imagen de la cámara.
     * @param imagen Imagen de la cámara.
     * @param region Región a la que pertenece la cámara.
     */
    public Camara(int id, String nombre, String latitud, String longitud, String urlImagen, int imagen, Region region) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.urlImagen = urlImagen;
        this.imagen = imagen;
        this.region = region;
    }

    // Getters y Setters para acceder y modificar los atributos de la cámara.

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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    /**
     * Método que que muestra los datos de la cámara
     */
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
