package com.rooted.controller;

import android.content.Context;
import com.rooted.model.DAOs.UsuarioDAO;

public class RegisterController {
    private UsuarioDAO usuarioDAO;

    public RegisterController(Context context) {
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public String registerUser(String username, String password, String confirmPassword) {
        // Validar campos vacíos
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return "Por favor, completa todos los campos";
        }

        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            return "Las contraseñas no coinciden";
        }

        // Intentar registrar el usuario
        boolean isRegistered = usuarioDAO.registerUser(username, password);
        if (isRegistered) {
            return "Usuario registrado exitosamente";
        } else {
            return "El nombre de usuario ya está en uso";
        }
    }
}
