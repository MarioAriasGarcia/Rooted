package com.rooted.model;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombreUsuario;
    private String password;

    public Usuario(int id, String nombreUsuario, String password) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getPassword() {
        return password;
    }
}
