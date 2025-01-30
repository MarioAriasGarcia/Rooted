package com.rooted;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.view.LoginActivity;
import com.rooted.view.MainActivity;
import com.rooted.view.RegisterActivity;

public class Main extends AppCompatActivity {

    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuarioDAO = new UsuarioDAO(this);

        Intent intent;
        String usernameSesionIniciada = usuarioDAO.readLastLogin();

        if (usernameSesionIniciada != null) {
            int userId = usuarioDAO.getUserIdByUsername(usernameSesionIniciada);

            if (userId != -1) {
                System.out.println("ESTOY EN EL MAIN, antes de ir a la mainactivity, el nombre de usuario y su id son: " + usernameSesionIniciada + " " + userId);

                intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", usernameSesionIniciada);
                intent.putExtra("user_id", userId);
            } else {
                usuarioDAO.deleteLastLogin();
                intent = new Intent(this, LoginActivity.class);
            }
        } else {
            usuarioDAO.deleteLastLogin();
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

}