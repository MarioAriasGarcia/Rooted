package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.HuertoController;
import com.rooted.controller.PlantaController;
import com.rooted.controller.SoporteController;
import com.rooted.ui.theme.MainActivity;

import java.util.ArrayList;

public class AddPlantaActivity extends AppCompatActivity{
    private EditText nombreHuertoEditText;

    private PlantaController plantaController;
    String tipoSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_planta);

        // Obtener el ID del huerto desde el Intent
        int huertoId = getIntent().getIntExtra("huertoId", -1);

        if (huertoId == -1) {
            Toast.makeText(this, "ID de huerto no válido", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar actividad si el ID no es válido
            return;
        }

        // Inicializar el controlador
        plantaController = new PlantaController(this);

        configurarUI(huertoId); // Pasar el ID del huerto
    }

    private void configurarUI(int huertoId) {
        // Configuración del botón para guardar la planta
        Button guardarPlantaButton = findViewById(R.id.guardar_planta_button);
        guardarPlantaButton.setOnClickListener(v -> {
            if (tipoSeleccionado == null) {
                Toast.makeText(this, "Selecciona una planta", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean plantaAñadida = plantaController.añadirPlanta(tipoSeleccionado, huertoId);
            if (plantaAñadida) {
                Toast.makeText(this, tipoSeleccionado + " añadido correctamente al huerto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error añadiendo la planta", Toast.LENGTH_SHORT).show();
            }

            // Regresar a la actividad anterior
            finish();
        });

        // Configuración del spinner
        Spinner spinner = findViewById(R.id.spinner_plantas);
        ArrayList<String> listaSpinner = new ArrayList<>();
        listaSpinner.add("Tomate");
        listaSpinner.add("Lechuga");
        listaSpinner.add("Garbanzos");
        listaSpinner.add("Pimientos");
        listaSpinner.add("Cebolla");
        listaSpinner.add("Lentejas");
        listaSpinner.add("Frijoles");
        listaSpinner.add("Habas");
        listaSpinner.add("Guisantes");
        listaSpinner.add("Patata");
        listaSpinner.add("Yuca");
        listaSpinner.add("Pepino");
        listaSpinner.add("Calabaza");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSpinner);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSeleccionado = parent.getItemAtPosition(position).toString(); // Guardar tipo seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoSeleccionado = null;
            }
        });

    }
}
