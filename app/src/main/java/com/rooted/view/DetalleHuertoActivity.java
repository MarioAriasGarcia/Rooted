package com.rooted.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;

public class DetalleHuertoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_huerto);

        // Obtenemos datos pasados desde la actividad anterior
        String nombreHuerto = getIntent().getStringExtra("nombre_huerto");

        // Mostramos el nombre del huerto
        TextView nombreTextView = findViewById(R.id.nombre_huerto);
        nombreTextView.setText(nombreHuerto);
    }
}
