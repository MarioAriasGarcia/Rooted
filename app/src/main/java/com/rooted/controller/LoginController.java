package com.rooted.controller;

import android.content.Context;
import com.rooted.model.entities.Usuario;
import com.rooted.model.DAOs.UsuarioDAO;

public class LoginController {
    private UsuarioDAO usuarioDAO;

    public LoginController(Context context) {
        this.usuarioDAO = new UsuarioDAO(context);
    }

    // Método para validar las credenciales
    public boolean validateLogin(String username, String password) {
        return usuarioDAO.validateUser(username, password);
    }

    // Método para obtener un usuario
    public Usuario getUserByUsername(String username) {
        int userId = usuarioDAO.getUserIdByUsername(username);
        if (userId != -1) {
            return new Usuario(userId, username, ""); // No se retorna el password por seguridad
        }
        return null;
    }
}
