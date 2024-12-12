package com.rooted.model;

import java.util.ArrayList;
import java.util.List;

public class Planta {
    private String nombre;
    private String tipo;
    private String fechaCuidado;

    public Planta(String nombre, String tipo, String fechaCuidado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaCuidado = fechaCuidado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCuidado() {
        return fechaCuidado;
    }

    public void setFechaCuidado(String fechaCuidado) {
        this.fechaCuidado = fechaCuidado;
    }
}