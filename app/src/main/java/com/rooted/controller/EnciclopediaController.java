package com.rooted.controller;

import android.content.Context;
import android.util.Log;

import com.rooted.model.DAOs.EnciclopediaDAO;
import com.rooted.model.DAOs.HuertoDAO;
import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.model.entities.Enciclopedia;
import com.rooted.model.entities.EntradasEnciclopedia;
import com.rooted.model.entities.Huerto;
import com.rooted.model.entities.Planta;
import com.rooted.model.DAOs.PlantaDAO;

import java.util.ArrayList;
import java.util.List;
public class EnciclopediaController {

    Enciclopedia enciclopedia = Enciclopedia.getInstance();

    private EnciclopediaDAO enciclopediaDAO;

    public EnciclopediaController(Context context) {
        this.enciclopediaDAO = new EnciclopediaDAO(context);
       // Log.d("EnciclopediaController", "Insertando datos en la base de datos...");
        enciclopediaDAO.insertarDatosEnciclopedia();
    }

    public EntradasEnciclopedia buscarPlanta(String tipoPlanta) {
        return enciclopediaDAO.buscarPlanta(tipoPlanta);
    }

    public boolean addEntradaEnciclopedia(EntradasEnciclopedia entrada){
        return enciclopediaDAO.addEntradaEnciclopedia(entrada);
    }


    public ArrayList<String> getAllPlantasEnciclopedia(){
        return enciclopediaDAO.getAllPlantasEnciclopedia();
    }



}
