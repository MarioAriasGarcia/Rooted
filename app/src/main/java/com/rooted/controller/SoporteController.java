package com.rooted.controller;

import android.content.Context;
import com.rooted.model.Mensaje;
import com.rooted.model.MensajeDAO;
import com.rooted.model.MensajeReporte;
import com.rooted.model.MensajeSoporte;

public class SoporteController {
    private MensajeDAO mensajeDAO;

    public SoporteController(Context context) {
        this.mensajeDAO = new MensajeDAO(context);
    }

    // Método para guardar un mensaje genérico
    public boolean guardarMensaje(String tipo, String errorConsulta, String contenido) {
        Mensaje mensaje;

        if (tipo.equals("Reporte")) {
            mensaje = new MensajeReporte(tipo, errorConsulta, contenido);
        } else if (tipo.equals("Sugerencia")) {
            mensaje = new MensajeSoporte(tipo, errorConsulta, contenido);
        } else {
            return false; // Tipo no válido
        }
        return mensajeDAO.insertMensaje(mensaje);
    }
}
