package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class Ciudad implements Serializable {
    private int id;
    private String nombre;
    private String latitud;
    private String longitud;
    private Provincia provincia;
    private List<Incidencia> incidencias;

    public Ciudad() {
    }

    public Ciudad(int id, String nombre, String latitud, String longitud, Provincia provincia, List<Incidencia> incidencias) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.provincia = provincia;
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

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}
