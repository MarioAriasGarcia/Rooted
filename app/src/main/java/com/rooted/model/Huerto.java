package com.rooted.model;

import java.util.ArrayList;
import java.util.List;

public class Huerto {

    protected int id;
    private String nombre;
    private ArrayList<Planta> plantas;
    private int size;

    public Huerto(int id, String nombre, int size) {
        this.id = id;
        this.nombre = nombre;
        this.plantas = new ArrayList<>();
        this.size = size;
    }

    public int getId(){
        return id;
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

    public int getSize(){
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }
}