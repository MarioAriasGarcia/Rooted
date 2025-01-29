package com.rooted.controller;

import android.content.Context;

import com.rooted.model.DAOs.HuertoDAO;
import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.model.entities.Huerto;
import com.rooted.model.entities.Planta;
import com.rooted.model.DAOs.PlantaDAO;

import java.util.List;

public class PlantaController {
    private PlantaDAO plantaDAO;
    private HuertoDAO huertoDAO;

    public PlantaController(Context context) {
        this.plantaDAO = new PlantaDAO(context);
        this.huertoDAO = new HuertoDAO(context);
    }

    public Planta añadirPlanta(String plantaNombre, int huertoId) {
        // Obtener el huerto desde la base de datos
        Huerto huerto = huertoDAO.getHuertoById(huertoId);
        if (huerto == null) return null; // Retorna null si el huerto no existe

        // Obtener los tiempos de riego y crecimiento desde el DAO
        int tiempoRiego = plantaDAO.getTiempoRiego(plantaNombre);
        int tiempoCrecimiento = plantaDAO.getTiempoCrecimiento(plantaNombre);

        // Crear la planta con los valores obtenidos
        Planta nuevaPlanta = new Planta(plantaNombre, tiempoRiego, tiempoCrecimiento);

        // Insertar la planta en la base de datos
        boolean insertada = plantaDAO.addPlanta(plantaNombre, huertoId);

        if (insertada) {
            // Añadir la planta al huerto localmente
            huerto.agregarPlanta(nuevaPlanta);
            return nuevaPlanta; // Retorna la planta creada
        }

        return null; // Retorna null si no se insertó correctamente
    }

    public List<Planta> obtenerPlantasPorHuerto(int huertoId){
        return plantaDAO.getPlantasByHuertoId(huertoId);
    }
}


