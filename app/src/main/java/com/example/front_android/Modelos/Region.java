package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;

public class Region implements Serializable {
    private int id;
    private int idRegion;
    private String nombreEs;
    private String nombreEu;

    /**
     * Constructor vacío.
     */
    public Region() {
    }

    /**
     * Constructor con parámetros para inicializar una región.
     * @param id Identificador único de la región.
     * @param idRegion Identificador adicional de la región.
     * @param nombreEs Nombre de la región en español.
     * @param nombreEu Nombre de la región en euskera.
     */
    public Region(int id, int idRegion, String nombreEs, String nombreEu) {
        this.id = id;
        this.idRegion = idRegion;
        this.nombreEs = nombreEs;
        this.nombreEu = nombreEu;
    }

    // Getters y Setters para acceder y modificar los atributos de la Region.

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

    /**
     * Método que muestra los datos de la Region.
     */
    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", idRegion=" + idRegion +
                ", nombreEs='" + nombreEs + '\'' +
                ", nombreEu='" + nombreEu + '\'' +
                '}';
    }
}
