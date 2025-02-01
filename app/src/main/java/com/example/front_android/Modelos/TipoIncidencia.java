package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class TipoIncidencia implements Serializable {
    private int id;
    private String nombre;
    private List<Incidencia> incidencias;

    /**
     * Constructor vacío
     */
    public TipoIncidencia() {
    }

    /**
     * Constructor con parámetros para inicializar un tipo de incidencia.
     * @param id Identificador único del tipo de incidencia.
     * @param nombre Nombre del tipo de incidencia.
     * @param incidencias Lista de incidencias asociadas a este tipo.
     */
    public TipoIncidencia(int id, String nombre, List<Incidencia> incidencias) {
        this.id = id;
        this.nombre = nombre;
        this.incidencias = incidencias;
    }

    // Getters y Setters para acceder y modificar los atributos de TipoIncidencial.

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

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
    /**
     * Método que muestra los datos de TipoIncidencia.
     */
    @Override
    public String toString() {
        return "TipoIncidencia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", incidencias=" + incidencias +
                '}';
    }
}
