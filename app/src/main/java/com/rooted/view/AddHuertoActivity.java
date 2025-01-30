package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.HuertoController;

public class AddHuertoActivity extends AppCompatActivity {

    private EditText nombreHuertoEditText, sizeHuertoEditText;
    private HuertoController huertoController;

    int userId;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_huerto);

        userId = getIntent().getIntExtra("user_id", -1);
        username = getIntent().getStringExtra("username");

        nombreHuertoEditText = findViewById(R.id.nombre_huerto_edittext);
        sizeHuertoEditText = findViewById(R.id.size_huerto_edittext);

        // Inicializa el controlador
        huertoController = new HuertoController(this);

        Button guardarHuertoButton = findViewById(R.id.guardar_huerto_button);
        guardarHuertoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarHuerto();
            }
        });

        Button volverAtrasButton = findViewById(R.id.volver_atras_button);
        volverAtrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHuertoActivity.this, GestionarHuertosActivity.class);
                intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
                intent.putExtra("username", username); // Pasar el nombre de usuario a la siguiente actividad
                startActivity(intent);
                finish();
            }
        });
    }

    private void agregarHuerto() {
        String nombreHuerto = nombreHuertoEditText.getText().toString().trim();
        String sizeHuertoString = sizeHuertoEditText.getText().toString().trim();

        int userId = getIntent().getIntExtra("user_id", -1); // Recupera el user_id del intent

        try {
            if (sizeHuertoString.isEmpty()) {
                throw new IllegalArgumentException("Por favor, ingresa un tamaño válido para el huerto.");
            }

            int sizeHuerto = Integer.parseInt(sizeHuertoString); // Convierte el tamaño a entero
            huertoController.agregarHuerto(nombreHuerto, sizeHuerto, userId); // Delegar la lógica al controlador

            Toast.makeText(this, "Huerto añadido correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Vuelve a la pantalla anterior
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingresa un número válido para el tamaño", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
