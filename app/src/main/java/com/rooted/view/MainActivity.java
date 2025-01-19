package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.rooted.R;

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

        // Mostrar un mensaje de bienvenida
        Toast.makeText(this, "Bienvenido, usuario: " + nombreUsuario, Toast.LENGTH_SHORT).show();

        // Configurar los botones
        Button gestionarHuertosButton = findViewById(R.id.gestionar_huertos_button);
        gestionarHuertosButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GestionarHuertosActivity.class);
            intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
            intent.putExtra("username", nombreUsuario); // Pasar el nombre de usuario a la siguiente actividad
            startActivity(intent);
        });

        Button tutorialesButton = findViewById(R.id.tutoriales_button);
        tutorialesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TutorialesActivity.class);
            startActivity(intent);
        });

        Button enciclopediaButton = findViewById(R.id.enciclopedia_button);
        enciclopediaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EnciclopediaActivity.class);
            startActivity(intent);
        });

        Button soporteButton = findViewById(R.id.soporte_button);
        soporteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SoporteActivity.class);
            startActivity(intent);
        });
    }
}
