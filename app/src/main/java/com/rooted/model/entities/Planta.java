package com.rooted.model.entities;

import com.rooted.model.DAOs.PlantaDAO;

public class Planta {
    private int id;
    private String nombre;
    private int tiempoRiego;
    private int tiempoCrecimiento;

    public Planta(String nombre, int tiempoRiego, int tiempoCrecimiento) {
        this.nombre = nombre;
        this.tiempoRiego = tiempoRiego;
        this.tiempoCrecimiento = tiempoCrecimiento;
    }

    public int getId(){
        return this.id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempoRiego() {
        return tiempoRiego;
    }

    public void setTiempoRiego(int tiempoRiego) {
        this.tiempoRiego = tiempoRiego;
    }

    public int getTiempoCrecimiento() {
        return this.tiempoCrecimiento;
    }

    public void setTiempoCrecimiento(int tiempoCrecimiento) {
        this.tiempoCrecimiento = tiempoCrecimiento;
    }
}