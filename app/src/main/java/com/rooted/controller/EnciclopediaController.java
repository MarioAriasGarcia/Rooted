package com.rooted.controller;

import android.content.Context;

import com.rooted.model.DAOs.EnciclopediaDAO;
import com.rooted.model.DAOs.HuertoDAO;
import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.model.entities.Enciclopedia;
import com.rooted.model.entities.EntradasEnciclopedia;
import com.rooted.model.entities.Huerto;
import com.rooted.model.entities.Planta;
import com.rooted.model.DAOs.PlantaDAO;

import java.util.List;
public class EnciclopediaController {

    private Enciclopedia enciclopedia = new Enciclopedia();

    private EnciclopediaDAO enciclopediaDAO;

    public EnciclopediaController(Context context) {
        this.enciclopedia = enciclopedia.getInstance();
        this.enciclopediaDAO = new EnciclopediaDAO(context);
        enciclopediaDAO.insertarDatosEnciclopedia();
    }

    public  EntradasEnciclopedia buscarPlanta(String tipoPlanta) {
        return enciclopedia.buscarPlanta(tipoPlanta);
    }






}
