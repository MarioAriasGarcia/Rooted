package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.database.DatabaseHelper;
import com.rooted.ui.theme.MainActivity;

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

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
