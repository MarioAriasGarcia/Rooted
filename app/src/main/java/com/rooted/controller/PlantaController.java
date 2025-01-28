package com.rooted.controller;

import android.content.Context;

import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.model.entities.Planta;
import com.rooted.model.DAOs.PlantaDAO;

public class PlantaController {
    private PlantaDAO plantaDAO;

    public PlantaController(Context context){
        this.plantaDAO = new PlantaDAO(context);
    }

    public boolean añadirPlanta(String plantaNombre, int huertoId) {
        // Crear una instancia de la planta
        Planta nuevaPlanta = new Planta(plantaNombre, plantaDAO.getTiempoRiego(plantaNombre), plantaDAO.getTiempoCrecimiento(plantaNombre));

        // Agregar a la base de datos
        boolean insertada = plantaDAO.addPlanta(plantaNombre, huertoId);

        // Si se insertó correctamente, devuelve true
        return insertada;
    }

}
