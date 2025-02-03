package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.EnciclopediaController;

import java.util.ArrayList;

public class EnciclopediaActivity extends AppCompatActivity {

    int userId;
    String nombreUsuario;
    boolean isAdmin;
    String tipoSeleccionado;

    EnciclopediaController enciclopediaController = new EnciclopediaController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enciclopedia);

        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(EnciclopediaActivity.this, MainActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("username", nombreUsuario);
            startActivity(intent);
            finish();
        });

        Button buscarButton = findViewById(R.id.buscar_planta_button);
        buscarButton.setOnClickListener(v -> {
            if (tipoSeleccionado == null) return;

            Intent resultIntent = new Intent(EnciclopediaActivity.this, EnciclopediaPantaActivity.class);
            resultIntent.putExtra("user_id", userId);
            resultIntent.putExtra("username", nombreUsuario);
            resultIntent.putExtra("plantaNombre", tipoSeleccionado);
            startActivity(resultIntent);
            finish();
        });

        // Configuración del spinner
        Spinner spinner = findViewById(R.id.spinner_buscar_plantas);
        ArrayList<String> listaSpinner = new ArrayList<>();
        ArrayList<String> listaPlantas = new ArrayList<>();
        listaPlantas = enciclopediaController.getAllPlantasEnciclopedia();
        for(int i = 0; i < listaPlantas.size(); i++){
            listaSpinner.add(listaPlantas.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSpinner);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoSeleccionado = null;
            }
        });

        // Botón para agregar plantas (solo visible para administradores)
        Button agregarPlantaButton = findViewById(R.id.agregar_planta_button);
        if (isAdmin) {
            agregarPlantaButton.setVisibility(View.VISIBLE);
            agregarPlantaButton.setOnClickListener(v -> {
                Intent intent = new Intent(EnciclopediaActivity.this, AgregarPlantaEnciclopediaAdminActivity.class);
                intent.putExtra("user_id", userId);
                intent.putExtra("username", nombreUsuario);
                startActivity(intent);
            });
        } else {
            agregarPlantaButton.setVisibility(View.GONE);
        }
    }
}
