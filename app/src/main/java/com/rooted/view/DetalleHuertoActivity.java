package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.ui.theme.MainActivity;

public class DetalleHuertoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_huerto);

        // Obtener datos pasados desde la actividad anterior
        Intent intent = getIntent();
        String nombreHuerto = intent.getStringExtra("nombre");
        int sizeHuerto = intent.getIntExtra("size", -1); // Usa -1 como valor por defecto si no se pasa "size"

        // Validar datos
        if (nombreHuerto == null || sizeHuerto == -1) {
            Toast.makeText(this, "Datos del huerto no disponibles", Toast.LENGTH_SHORT).show();
            finish(); // Finaliza la actividad si los datos son inválidos
            return;
        }

        // Mostrar el nombre del huerto
        TextView nombreTextView = findViewById(R.id.nombre_huerto);
        nombreTextView.setText(nombreHuerto);

        // Mostrar el tamaño del huerto
        TextView sizeTextView = findViewById(R.id.size_huerto); // Asegúrate de que este ID exista en el layout
        sizeTextView.setText("Tamaño del huerto: " + String.valueOf(sizeHuerto) + "m^2"); // Convertir entero a cadena

        // Configurar botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_atras_button);
        volverMenuButton.setOnClickListener(v -> {
            Intent mainIntent = new Intent(DetalleHuertoActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        });
    }
}
