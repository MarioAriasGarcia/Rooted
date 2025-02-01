package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.PlantaController;
import com.rooted.database.DatabaseHelper;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        // Configurar base de datos
        databaseHelper = new DatabaseHelper(this);

        // Recuperar username y userId del Intent
        String username = getIntent().getStringExtra("username");
        int userId = getIntent().getIntExtra("user_id", -1);
        int plant_count = PlantaController.getPlant_count();

        // Verifica si userId o username son inválidos
        if (username == null) {
            Toast.makeText(this, "Error: No se pudo recuperar los datos del usuario por user", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else if (userId == -1) {
            Toast.makeText(this, "Error: No se pudo recuperar los datos del usuario por id", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Configurar los TextViews
        TextView usernameTextView = findViewById(R.id.username_textview); // Asume que este es el ID para el TextView de "Nombre de usuario"
        TextView userIdTextView = findViewById(R.id.user_id); // ID del TextView para "ID de usuario"
        TextView plant_countTextView = findViewById(R.id.plant_count); // ID del TextView para "Cantidad de plantas"

        // Establecer los valores en los TextViews
        usernameTextView.setText("" + username);
        userIdTextView.setText("" + userId);
        plant_countTextView.setText("" + plant_count);

        // Configurar el botón para volver al menú principal
        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }
}
