package com.example.front_android.Modelos;

import java.io.Serializable;

public class Rol implements Serializable {
    private int id;
    private String nombre;

    /**
     * Constructor vacío
     */
    public Rol() {
    }

    /**
     * Constructor con parámetros para inicializar un rol.
     * @param id Identificador único del rol.
     * @param nombre Nombre del rol.
     */
    public Rol(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters para acceder y modificar los atributos de Rol.

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
