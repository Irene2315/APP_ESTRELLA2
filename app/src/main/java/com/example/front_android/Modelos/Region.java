package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class Region implements Serializable {
    private int id;
    private String nombreEs;
    private String nombreEus;

    private List<Camara> camaras;
    private List<Incidencia> incidencias;

    public Region() {
    }

    public Region(int id, String nombreEs, String nombreEus, List<Camara> camaras, List<Incidencia> incidencias) {
        this.id = id;
        this.nombreEs = nombreEs;
        this.nombreEus = nombreEus;
        this.camaras = camaras;
        this.incidencias = incidencias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEs() {
        return nombreEs;
    }

    public void setNombreEs(String nombreEs) {
        this.nombreEs = nombreEs;
    }

    public String getNombreEus() {
        return nombreEus;
    }

    public void setNombreEus(String nombreEus) {
        this.nombreEus = nombreEus;
    }

    public List<Camara> getCamaras() {
        return camaras;
    }

    public void setCamaras(List<Camara> camaras) {
        this.camaras = camaras;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}
