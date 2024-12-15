package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.database.DatabaseHelper;
import com.rooted.model.Huerto;

public class AddHuertoActivity extends AppCompatActivity {

    private EditText nombreHuertoEditText, sizeHuertoEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_huerto);

        // Inicializamos los campos y botones
        nombreHuertoEditText = findViewById(R.id.nombre_huerto);
        sizeHuertoEditText = findViewById(R.id.size_huerto);
        Button guardarButton = findViewById(R.id.guardar_huerto_button);
        databaseHelper = new DatabaseHelper(this);

        guardarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = nombreHuertoEditText.getText().toString().trim();
                String sizeStr = sizeHuertoEditText.getText().toString().trim();

                if (nombre.isEmpty() || sizeStr.isEmpty()) {
                    Toast.makeText(AddHuertoActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int tamaño = Integer.parseInt(sizeStr);

                        // Guardamos el huerto en la base de datos
                        long huertoId = databaseHelper.insertHuerto(nombre, tamaño);

                        if (huertoId != -1) {
                            Toast.makeText(AddHuertoActivity.this, "Huerto añadido correctamente", Toast.LENGTH_SHORT).show();

                            // Abrimos la nueva actividad del huerto
                            Intent intent = new Intent(AddHuertoActivity.this, DetalleHuertoActivity.class);
                            intent.putExtra("huerto_id", huertoId); // Pasamos el ID del huerto
                            intent.putExtra("nombre_huerto", nombre); // Pasamos el nombre del huerto
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AddHuertoActivity.this, "Error al añadir el huerto", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(AddHuertoActivity.this, "El tamaño debe ser un número válido", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
