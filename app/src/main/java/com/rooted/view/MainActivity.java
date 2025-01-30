package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.model.DAOs.UsuarioDAO;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperar el user_id y el username desde el Intent
        int userId = getIntent().getIntExtra("user_id", -1);
        String nombreUsuario = getIntent().getStringExtra("username");

        // Verificar que el user_id sea válido
        if (userId == -1) {
            Toast.makeText(this, "Error: No se pudo cargar el ID del usuario", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar actividad si no hay un user_id válido
            return;
        }

        System.out.println("ESTOY EN MAINACTIVITY, El nombre del usuario es: " + nombreUsuario + " y su id es: " + userId);

        // Mostrar un mensaje de bienvenida
        Toast.makeText(this, "Menú principal", Toast.LENGTH_SHORT).show();

        // Configurar los botones
        Button gestionarHuertosButton = findViewById(R.id.gestionar_huertos_button);
        gestionarHuertosButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, GestionarHuertosActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("username", nombreUsuario);
        startActivity(intent);
    });

        Button tutorialesButton = findViewById(R.id.tutoriales_button);
        tutorialesButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, TutorialesActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("username", nombreUsuario);
        startActivity(intent);
    });

        Button enciclopediaButton = findViewById(R.id.enciclopedia_button);
        enciclopediaButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, EnciclopediaActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("username", nombreUsuario);
        startActivity(intent);
    });

        Button soporteButton = findViewById(R.id.soporte_button);
        soporteButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, SoporteActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("username", nombreUsuario);
        startActivity(intent);
    });

        Button miPerfilButton = findViewById(R.id.miPerfil_button);
        miPerfilButton.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("username", nombreUsuario);
        startActivity(intent);
    });

        Button cerrarSesionButton = findViewById(R.id.cerrar_sesion_button);
        cerrarSesionButton.setOnClickListener(v -> {
        // Eliminar el último inicio de sesión
        UsuarioDAO usuarioDAO = new UsuarioDAO(MainActivity.this);
        usuarioDAO.deleteLastLogin();

        // Redirigir a la pantalla de inicio de sesión
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Evita que el usuario regrese con el botón "Atrás"
        startActivity(intent);
        finish();
    });

    }
}
