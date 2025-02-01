package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.EnciclopediaController;

public class EnciclopediaPantaActivity extends AppCompatActivity {

    private EnciclopediaController enciclopediaController;
    int userId;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta_enciclopedia);

        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");

        // Inicializar controlador
        enciclopediaController = new EnciclopediaController();

        // Obtener el nombre de la planta desde el Intent
        String nombrePlanta = getIntent().getStringExtra("plantaNombre");

        // Obtener los datos de la planta
        String datoNombre = enciclopediaController.buscarPlanta(nombrePlanta).toStringNombre();
        String datoDescripcion = enciclopediaController.buscarPlanta(nombrePlanta).toStringDescripcion();
        String datoRiego = enciclopediaController.buscarPlanta(nombrePlanta).toStringRiego();
        String datoPlantar = enciclopediaController.buscarPlanta(nombrePlanta).toStringFormaPlantar();
        String datoRecoger = enciclopediaController.buscarPlanta(nombrePlanta).toStringFormaRecoger();

        // Enlazar los TextView con sus respectivos IDs
        TextView nombreTextView = findViewById(R.id.nombre_textview);
        TextView descripcionTextView = findViewById(R.id.descripcion_textview);
        TextView riegoTextView = findViewById(R.id.riego_textview);
        TextView plantarTextView = findViewById(R.id.plantar_textview);
        TextView cosecharTextView = findViewById(R.id.cosechar_textview);

        // Asignar los valores obtenidos a los TextView
        nombreTextView.setText(datoNombre);
        descripcionTextView.setText(datoDescripcion);
        riegoTextView.setText(datoRiego);
        plantarTextView.setText(datoPlantar);
        cosecharTextView.setText(datoRecoger);

        // Configurar botón de volver al menú
        Button volverEnciclopediaButton = findViewById(R.id.volver_enciclopedia_button);
        volverEnciclopediaButton.setOnClickListener(v -> {
            Intent intent = new Intent(EnciclopediaPantaActivity.this, EnciclopediaActivity.class);
            intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
            intent.putExtra("username", nombreUsuario); // Pasar el nombre de usuario a la siguiente actividad
            System.out.println("ESTOY EN ENCICLOPEDIA ACTIVITY, El nombre del usuario es: " + nombreUsuario + " y su id es: " + userId);
            startActivity(intent);
            finish();
        });
    }
}
