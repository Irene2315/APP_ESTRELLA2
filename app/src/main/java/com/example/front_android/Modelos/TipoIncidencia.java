package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class TipoIncidencia implements Serializable {
    private int id;
    private String nombre;
    private List<Incidencia> incidencias;

    public TipoIncidencia() {
    }

    public TipoIncidencia(int id, String nombre, List<Incidencia> incidencias) {
        this.id = id;
        this.nombre = nombre;
        this.incidencias = incidencias;
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

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    @Override
    public String toString() {
        return "TipoIncidencia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", incidencias=" + incidencias +
                '}';
    }
}
