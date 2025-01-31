package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.model.entities.Planta;

import java.util.ArrayList;

public class EnciclopediaActivity extends AppCompatActivity {

    int userId;
    String nombreUsuario;

    String tipoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enciclopedia);

        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnciclopediaActivity.this, MainActivity.class);
                intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
                intent.putExtra("username", nombreUsuario); // Pasar el nombre de usuario a la siguiente actividad
                startActivity(intent);
                finish();
            }
        });

        Button buscarButton = findViewById(R.id.buscar_planta_button);
        buscarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoSeleccionado == null) {
                    //Toast.makeText(this, "Selecciona una planta", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tipoSeleccionado != null) {
                    //Toast.makeText(this, tipoSeleccionado + " añadido correctamente al huerto", Toast.LENGTH_SHORT).show();
                    // Devolver datos a la actividad anterior
                    Intent resultIntent = new Intent(EnciclopediaActivity.this, EnciclopediaPantaActivity.class);
                    resultIntent.putExtra("plantaNombre", tipoSeleccionado);
                    startActivity(resultIntent);
                }


                finish();
            }

        });
        // Configuración del spinner
        Spinner spinner = findViewById(R.id.spinner_buscar_plantas);
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