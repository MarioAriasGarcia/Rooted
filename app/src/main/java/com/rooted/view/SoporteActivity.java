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
import com.rooted.controller.SoporteController;

import java.util.ArrayList;

public class SoporteActivity extends AppCompatActivity {

    private EditText textoTipo, textoContenido;
    private String tipoSeleccionado; // Tipo seleccionado en el spinner
    private SoporteController soporteController; // Controlador

    private int userId;
    private String nombreUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporte);

        // Recuperar datos del Intent
        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        // Inicializamos el controlador
        soporteController = new SoporteController(this);

        // Configurar los botones y campos de texto
        configurarUI();
    }

    private void configurarUI() {
        // Configuración del botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(SoporteActivity.this, MainActivity.class);
            intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
            intent.putExtra("username", nombreUsuario); // Pasar el nombre de usuario a la siguiente actividad
            startActivity(intent);
            finish();
        });

        // Configuración del spinner
        Spinner spinner = findViewById(R.id.spinner_soporte);
        ArrayList<String> listaSpinner = new ArrayList<>();
        listaSpinner.add("Reporte");
        listaSpinner.add("Sugerencia");
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

        textoTipo = findViewById(R.id.soporte_texto_tipo);
        textoContenido = findViewById(R.id.soporte_texto);

        // Configuración del botón para enviar
        Button enviarButton = findViewById(R.id.enviar_button);
        enviarButton.setOnClickListener(v -> guardarMensaje());
    }

    private void guardarMensaje() {
        // Obtenemos los datos de los campos de texto
        String tipoErrorConsulta = textoTipo.getText().toString().trim();
        String contenidoMensaje = textoContenido.getText().toString().trim();

        if (tipoSeleccionado == null || tipoErrorConsulta.isEmpty() || contenidoMensaje.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Delegar al controlador para guardar el mensaje
        boolean isInserted = soporteController.guardarMensaje(tipoSeleccionado, tipoErrorConsulta, contenidoMensaje, userId);

        if (isInserted) {
            Toast.makeText(this, "Mensaje guardado correctamente", Toast.LENGTH_SHORT).show();
            // Limpiar campos después de guardar
            textoTipo.setText("");
            textoContenido.setText("");
        } else {
            Toast.makeText(this, "Error al guardar el mensaje", Toast.LENGTH_SHORT).show();
        }
    }
}
