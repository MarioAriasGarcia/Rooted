package com.rooted.controller;

import android.content.Context;
import com.rooted.model.entities.Mensaje;
import com.rooted.model.DAOs.MensajeDAO;
import com.rooted.model.entities.MensajeReporte;
import com.rooted.model.entities.MensajeSoporte;

import java.util.List;

public class SoporteController {
    private MensajeDAO mensajeDAO;

    public SoporteController(Context context) {
        this.mensajeDAO = new MensajeDAO(context);
    }

    // Método para guardar un mensaje genérico
    public boolean guardarMensaje(String tipo, String errorConsulta, String contenido, int userId) {
        Mensaje mensaje;

        if (tipo.equals("Reporte")) {
            mensaje = new MensajeReporte(tipo, errorConsulta, contenido);
        } else if (tipo.equals("Sugerencia")) {
            mensaje = new MensajeSoporte(tipo, errorConsulta, contenido);
        } else {
            return false; // Tipo no válido
        }
        return mensajeDAO.insertMensaje(mensaje, userId);
    }

    public List<String[]> obtenerTodosLosMensajes() {
        return mensajeDAO.obtenerTodosLosMensajes();
    }
}
