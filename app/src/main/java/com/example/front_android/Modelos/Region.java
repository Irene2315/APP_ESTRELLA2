package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class Region implements Serializable {
    private int id;
    private int idRegion;
    private String nombreEs;
    private String nombreEu;

    private List<Camara> camaras;
    private List<Incidencia> incidencias;

    public Region() {
    }

    public Region(int id, int idRegion, String nombreEs, String nombreEu, List<Camara> camaras, List<Incidencia> incidencias) {
        this.id = id;
        this.idRegion = idRegion;
        this.nombreEs = nombreEs;
        this.nombreEu = nombreEu;
        this.camaras = camaras;
        this.incidencias = incidencias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRegion() {
        return idRegion;
    }

    public void setIdRegion(int idRegion) {
        this.idRegion = idRegion;
    }

    public String getNombreEs() {
        return nombreEs;
    }

    public void setNombreEs(String nombreEs) {
        this.nombreEs = nombreEs;
    }

    public String getNombreEu() {
        return nombreEu;
    }

    public void setNombreEu(String nombreEu) {
        this.nombreEu = nombreEu;
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
