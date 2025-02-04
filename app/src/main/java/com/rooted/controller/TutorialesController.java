package com.rooted.controller;

import android.content.Context;

import com.rooted.model.DAOs.TutorialDAO;

public class TutorialesController {
    private TutorialDAO tutorialDAO;
    public TutorialesController(Context context){this.tutorialDAO = new TutorialDAO(context);}
    public void addTutorialC(String nombre_video, String uri){
        tutorialDAO.addVideo(nombre_video, uri);
    }
    public String obtenerVideo(String nombreVideo){
        return tutorialDAO.obtenerTutorialesUri(nombreVideo);
    }
}
//Hacer un atribusto lista en el activity para guardar todos los nombres, y que el controller y el dao devuelvan el uri