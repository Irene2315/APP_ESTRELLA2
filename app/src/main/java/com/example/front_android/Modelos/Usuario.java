package com.example.front_android.Modelos;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String correoElectronico;
    private String contrasena;
    private Rol rol;

    public Usuario() {
    }

    public Usuario(int id, String correoElectronico, String contrasena, String nombre, Rol rol) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.rol = rol;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
