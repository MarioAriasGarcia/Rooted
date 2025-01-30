package com.rooted;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.model.DAOs.UsuarioDAO;
import com.rooted.view.LoginActivity;
import com.rooted.view.MainActivity;
import com.rooted.view.RegisterActivity;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        Intent intent;
        String usernameSesionIniciada = usuarioDAO.readLastLogin();

        if (usernameSesionIniciada != null) {
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", usernameSesionIniciada);
            intent.putExtra("user_id", usuarioDAO.getUserIdByUsername(usernameSesionIniciada));

        }
        else {
            intent = new Intent(this, LoginActivity.class);
        }

        //usuarioDAO.closeConnection();
        startActivity(intent);
        finish();
    }
}