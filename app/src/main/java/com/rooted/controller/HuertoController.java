package com.rooted.controller;

import android.content.Context;

import com.rooted.model.Huerto;
import com.rooted.model.HuertoDAO;

import java.util.List;

public class HuertoController {
    private HuertoDAO huertoDAO;

    public HuertoController(Context context) {
        this.huertoDAO = new HuertoDAO(context);
    }

    // Agregar un nuevo huerto con validaciones
    public boolean agregarHuerto(String nombreHuerto, int size, int userId) {
        if (nombreHuerto == null || nombreHuerto.isEmpty()) {
            throw new IllegalArgumentException("El nombre del huerto no puede estar vacío.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("El tamaño del huerto debe ser mayor que 0.");
        }
        if (userId <= 0) {
            throw new IllegalArgumentException("El ID del usuario no es válido.");
        }

        return huertoDAO.insertHuerto(nombreHuerto, size, userId);
    }

    // Obtener todos los huertos asociados a un usuario
    public List<Huerto> obtenerHuertosPorUsuario(int userId) {
        return huertoDAO.getHuertosByUserId(userId);
    }
}
