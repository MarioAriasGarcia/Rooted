package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;

public class DetalleHuertoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_huerto);

        // Obtenemos datos pasados desde la actividad anterior
        String nombreHuerto = getIntent().getStringExtra("nombre");
        int sizeHuerto = Integer.parseInt(getIntent().getStringExtra("size"));

        // Mostramos el nombre del huerto
        TextView nombreTextView = findViewById(R.id.nombre_huerto);
        nombreTextView.setText(nombreHuerto);

        TextView sizeTextView = findViewById(R.id.nombre_huerto);
        sizeTextView.setText(sizeHuerto);


        // Configurar botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalleHuertoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
