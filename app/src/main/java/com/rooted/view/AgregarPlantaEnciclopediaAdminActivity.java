package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.EnciclopediaController;
import com.rooted.model.entities.EntradasEnciclopedia;

public class AgregarPlantaEnciclopediaAdminActivity extends AppCompatActivity {

    EditText nombrePlanta, descripcionPlanta, riegoPlanta, formaPlantar, formaRecoger;
    Button btnAgregar, btnVolver;
    int userId;
    String nombreUsuario;

    EnciclopediaController enciclopediaController = new EnciclopediaController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_planta_enciclopedia);

        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");

        nombrePlanta = findViewById(R.id.edit_nombre_planta);
        descripcionPlanta = findViewById(R.id.edit_descripcion);
        riegoPlanta = findViewById(R.id.edit_riego);
        formaPlantar = findViewById(R.id.edit_forma_plantar);
        formaRecoger = findViewById(R.id.edit_forma_recoger);
        btnAgregar = findViewById(R.id.btn_agregar_planta);
        btnVolver = findViewById(R.id.btn_volver);

        btnAgregar.setOnClickListener(v -> {
            String nombre = nombrePlanta.getText().toString().trim();
            String descripcion = descripcionPlanta.getText().toString().trim();
            String riego = riegoPlanta.getText().toString().trim();
            String plantar = formaPlantar.getText().toString().trim();
            String recoger = formaRecoger.getText().toString().trim();

            if (nombre.isEmpty() || descripcion.isEmpty() || riego.isEmpty() || plantar.isEmpty() || recoger.isEmpty()) {
                Toast.makeText(AgregarPlantaEnciclopediaAdminActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            EntradasEnciclopedia nuevaPlanta = new EntradasEnciclopedia(nombre, descripcion, riego, plantar, recoger);
            boolean isInserted = enciclopediaController.addEntradaEnciclopedia(nuevaPlanta);

            if(isInserted){
                Toast.makeText(AgregarPlantaEnciclopediaAdminActivity.this, "Planta agregada correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(AgregarPlantaEnciclopediaAdminActivity.this, "Error agregando la planta", Toast.LENGTH_SHORT).show();
            }


            // Regresar a la enciclopedia
            Intent intent = new Intent(AgregarPlantaEnciclopediaAdminActivity.this, EnciclopediaActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("username", nombreUsuario);
            intent.putExtra("isAdmin", true);
            startActivity(intent);
            finish();
        });

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(AgregarPlantaEnciclopediaAdminActivity.this, EnciclopediaActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("username", nombreUsuario);
            intent.putExtra("isAdmin", true);
            startActivity(intent);
            finish();
        });
    }
}
