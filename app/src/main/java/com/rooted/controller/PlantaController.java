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

    public boolean añadirPlanta(String planta){
        if(planta.equals("Tomate")){
            return true;
        }else{
            return false;
        }
    }
}
