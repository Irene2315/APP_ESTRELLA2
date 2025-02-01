package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;


public class Provincia implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;

    private List<Ciudad> ciudades;

    /**
     * Constructor vacío.
     */
    public Provincia() {
    }

    /**
     * Constructor con parámetros para inicializar una provincia.
     * @param ciudades Lista de ciudades pertenecientes a la provincia.
     * @param id Identificador único de la provincia.
     * @param latitud Latitud geográfica de la provincia.
     * @param longitud Longitud geográfica de la provincia.
     * @param nombre Nombre de la provincia.
     */
    public Provincia(List<Ciudad> ciudades, int id, String latitud, String longitud, String nombre) {
        this.ciudades = ciudades;
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
    }

    // Getters y Setters para acceder y modificar los atributos de la Provincia.

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

    /**
     * Método que muestra los datos de la Provincia.
     */
    @Override
    public String toString() {
        return "Provincia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                ", ciudades=" + ciudades +
                '}';
    }
}
