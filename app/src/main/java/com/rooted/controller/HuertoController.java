package com.rooted.controller;

import com.rooted.model.Huerto;
import com.rooted.model.Planta;

public class HuertoController {
    private Huerto huerto;

    public HuertoController(Huerto huerto) {
        this.huerto = huerto;
    }

    public void agregarPlanta(String nombre, String tipo, String fechaCuidado) {
        Planta nuevaPlanta = new Planta(nombre, tipo, fechaCuidado);
        huerto.agregarPlanta(nuevaPlanta);
    }

    public void mostrarPlantas() {
        for (Planta planta : huerto.getPlantas()) {
            System.out.println("Planta: " + planta.getNombre() + ", Tipo: " + planta.getTipo());
        }
    }
}