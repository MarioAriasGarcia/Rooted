package com.rooted.controller;

import android.content.Context;
import com.rooted.model.DAOs.UsuarioDAO;

public class RegisterController {
    private UsuarioDAO usuarioDAO;

    public RegisterController(Context context) {
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public boolean registerUser(String username, String password) {
        // Intentar registrar el usuario
        return usuarioDAO.registerUser(username, password);
    }

}
