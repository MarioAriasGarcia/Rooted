package com.rooted.controller;

import android.content.Context;
import android.content.Intent;

import com.rooted.model.DAOs.HuertoDAO;
import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.model.entities.Huerto;
import com.rooted.model.entities.Planta;
import com.rooted.model.DAOs.PlantaDAO;

import java.util.ArrayList;
import java.util.List;

public class PlantaController {
    private PlantaDAO plantaDAO;
    private HuertoDAO huertoDAO;

    private static int plant_count = 0;

    public PlantaController(Context context) {
        this.plantaDAO = new PlantaDAO(context);
        this.huertoDAO = new HuertoDAO(context);
    }


    public static int  getPlant_count() {
        return plant_count;
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
            plant_count++;
            return nuevaPlanta; // Retorna la planta creada
        }

        return null; // Retorna null si no se insertó correctamente
    }

    public boolean yaEstaAñadida(String plantaNombre, int huertoId){
        ArrayList<String> plantas= huertoDAO.getPlantasByHuertoId(huertoId);
        for(int i = 0; i < plantas.size(); i++){
            if(plantaNombre.equals(plantas.get(i))){
                return true;
            }
        }
        return false;
    }

    public List<Planta> obtenerPlantasPorHuerto(int huertoId){
        return plantaDAO.getPlantasByHuertoId(huertoId);
    }

    public int obtenerTiempoRiego(String nombrePlanta){
        return plantaDAO.getTiempoRiego(nombrePlanta);
    }

    public String getSiguienteRiego(int plantaId){
        return plantaDAO.getSiguienteRiego(plantaId);
    }

    public int obtenerTiempoCrecimiento(String nombrePlanta){
        return plantaDAO.getTiempoCrecimiento(nombrePlanta);
    }

    public int obtenerPlantaIdPorNombreYHuerto(String plantaNombre, int huertoId){
        return plantaDAO.obtenerPlantaIdPorNombreYHuerto(plantaNombre, huertoId);
    }

    public void actualizarSiguienteRiego(int plantaId, String nuevaFechaRiego){
        plantaDAO.actualizarSiguienteRiego(plantaId, nuevaFechaRiego);
    }

    public boolean eliminarPlanta(int plantaId){
        return plantaDAO.eliminarPlanta(plantaId);
    }

    public void marcarComoRecogida(int plantaId){
        plantaDAO.marcarComoRecogida(plantaId);
    }

    public boolean isRecogida(int plantaId){
        return plantaDAO.isRecogida(plantaId);
    }
}


