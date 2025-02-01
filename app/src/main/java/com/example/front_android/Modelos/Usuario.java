package com.example.front_android.Modelos;

import java.io.Serializable;
import java.util.List;


public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String correoElectronico;
    private String contrasena;
    private Rol rol;

    /**
     * Constructor vacío
     */
    public Usuario() {
    }

    /**
     * Constructor con parámetros para inicializar un usuario.
     * @param id Identificador único del usuario.
     * @param correoElectronico Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     * @param nombre Nombre del usuario.
     * @param rol Rol asignado al usuario.
     */
    public Usuario(int id, String correoElectronico, String contrasena, String nombre, Rol rol) {
        this.id = id;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.rol = rol;
    }


    // Getters y Setters para acceder y modificar los atributos de Usuario.

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

    /**
     * Método que muestra los datos del Usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", rol=" + rol +
                '}';
    }
}
