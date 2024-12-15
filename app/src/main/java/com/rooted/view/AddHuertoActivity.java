package com.rooted.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.database.DatabaseHelper;

public class AddHuertoActivity extends AppCompatActivity {

    private EditText nombreHuertoEditText, sizeHuertoEditText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_huerto);

        nombreHuertoEditText = findViewById(R.id.nombre_huerto_edittext);
        sizeHuertoEditText = findViewById(R.id.size_huerto_edittext);
        databaseHelper = new DatabaseHelper(this);

        Button guardarHuertoButton = findViewById(R.id.guardar_huerto_button);
        guardarHuertoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreHuerto = nombreHuertoEditText.getText().toString().trim();
                String sizeHuertoString = sizeHuertoEditText.getText().toString().trim();

                int userId = getIntent().getIntExtra("user_id", -1); // Recupera el user_id

                if (nombreHuerto.isEmpty()) {
                    Toast.makeText(AddHuertoActivity.this, "Por favor, ingresa un nombre para el huerto", Toast.LENGTH_SHORT).show();
                } else if (sizeHuertoString.isEmpty()) {
                    Toast.makeText(AddHuertoActivity.this, "Por favor, ingresa un tamaño para el huerto", Toast.LENGTH_SHORT).show();
                } else if (userId == -1) {
                    Toast.makeText(AddHuertoActivity.this, "Fallo con el userId en la base de datos", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int sizeHuerto = Integer.parseInt(sizeHuertoString); // Convierte el texto a entero
                        if (sizeHuerto <= 0) {
                            Toast.makeText(AddHuertoActivity.this, "El tamaño debe ser mayor que 0", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseHelper.agregarHuerto(nombreHuerto, sizeHuerto, userId); // Crea huerto con el usuario correspondiente
                            Toast.makeText(AddHuertoActivity.this, "Huerto añadido correctamente", Toast.LENGTH_SHORT).show();
                            finish(); // Vuelve a la pantalla anterior
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(AddHuertoActivity.this, "Por favor, ingresa un número válido para el tamaño", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
