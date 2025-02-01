package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class Ciudad implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;

    /**
     * Constructor vacío para Ciudad
     */
    public Ciudad() {
    }

    /**
     * Constructor con parámetros para inicializar todos los atributos de la Ciudad
     * @param id Identificador de la ciudad.
     * @param nombre Nombre de la ciudad.
     * @param latitud Latitud de la ubicación de la ciudad.
     * @param longitud Longitud de la ubicación de la ciudad.
     */
    public Ciudad(int id, String nombre, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;

    }

    // Getters y Setters para acceder y modificar los atributos de la ciudad.

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

    /**
     * Método que que muestra los datos de la ciudad
     */
    @Override
    public String toString() {
        return "Ciudad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }
}
