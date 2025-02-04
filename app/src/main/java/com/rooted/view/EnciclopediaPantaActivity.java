package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.EnciclopediaController;

public class EnciclopediaPantaActivity extends AppCompatActivity {

    private EnciclopediaController enciclopediaController;
    int userId;
    String nombreUsuario;
    boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta_enciclopedia);

        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        // Inicializar controlador
        enciclopediaController = new EnciclopediaController(this);

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

        // Configurar botón de eliminar planta (solo para administradores)
        Button eliminarPlantaButton = findViewById(R.id.eliminar_planta_button);
        if (isAdmin) {
            eliminarPlantaButton.setVisibility(View.VISIBLE); // Mostrar el botón si es admin
        }

        // Configurar el listener para el botón de eliminar
        eliminarPlantaButton.setOnClickListener(v -> eliminarPlanta(nombrePlanta));
    }

    private void eliminarPlanta(String nombrePlanta) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar planta")
                .setMessage("¿Estás seguro de que deseas eliminar la planta '" + nombrePlanta + "'? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Llamar al controlador para eliminar la planta
                    boolean eliminada = enciclopediaController.eliminarPlanta(nombrePlanta);
                    if (eliminada) {
                        Toast.makeText(this, "Planta eliminada exitosamente", Toast.LENGTH_SHORT).show();
                        // Volver a la actividad de la enciclopedia
                        Intent intent = new Intent(EnciclopediaPantaActivity.this, EnciclopediaActivity.class);
                        intent.putExtra("user_id", userId);
                        intent.putExtra("username", nombreUsuario);
                        intent.putExtra("isAdmin", isAdmin);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
