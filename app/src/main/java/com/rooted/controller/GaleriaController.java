package com.rooted.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rooted.database.DatabaseHelper;
import com.rooted.model.DAOs.PlantaDAO;

import java.util.ArrayList;
import java.util.List;

public class GaleriaController {

    private PlantaDAO plantaDAO;

    public GaleriaController(Context context) {
        this.plantaDAO = new PlantaDAO(context);
    }

    // Insertar imagen
    public long agregarImagen(int plantaId, String uri) {
        return plantaDAO.agregarImagen(plantaId, uri);
    }

    // Obtener imágenes por planta
    public List<String> obtenerImagenesPorPlanta(int plantaId) {
       return plantaDAO.obtenerImagenesPorPlanta(plantaId);
    }

    // Eliminar imagen
    public void eliminarImagen(String uri) {
        plantaDAO.eliminarImagen(uri);
    }


}
