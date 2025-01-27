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

        // Inicializar el controlador
        plantaController = new PlantaController(this);

        configurarUI();

    }

    private void configurarUI() {
        // Configuración del botón para volver al menú
        Button guardarPlantaButton = findViewById(R.id.guardar_planta_button);
        guardarPlantaButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddPlantaActivity.this, DetalleHuertoActivity.class);
            //intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
            //intent.putExtra("username", nombreUsuario); // Pasar el nombre de usuario a la siguiente actividad
            boolean plantaAñadida = plantaController.añadirPlanta(tipoSeleccionado);
            if(plantaAñadida){
                Toast.makeText(AddPlantaActivity.this, tipoSeleccionado + " añadido correctamente" , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AddPlantaActivity.this, "Error añadiendo la planta" , Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);
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
