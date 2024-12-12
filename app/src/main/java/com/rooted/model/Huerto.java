package com.rooted.model;

import java.util.ArrayList;
import java.util.List;

public class Huerto {
    private String nombre;
    private List<Planta> plantas;

    public Huerto(String nombre) {
        this.nombre = nombre;
        this.plantas = new ArrayList<>();
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public List<Planta> getPlantas() {

        return plantas;
    }

    public void agregarPlanta(Planta planta) {

        plantas.add(planta);
    }
}