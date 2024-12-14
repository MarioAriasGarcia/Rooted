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
import com.rooted.database.DatabaseHelper;
import com.rooted.ui.theme.MainActivity;

import java.util.ArrayList;

public class SoporteActivity extends AppCompatActivity {

    private EditText textoTipo, textoContenido;
    private String tipoSeleccionado; // Variable para almacenar el valor seleccionado en el spinner
    private DatabaseHelper databaseHelper; // Instancia del helper de base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporte);

        // Inicializamos el helper de base de datos
        databaseHelper = new DatabaseHelper(this);

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoporteActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Spinner spinner = findViewById(R.id.spinner_soporte);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSeleccionado = parent.getItemAtPosition(position).toString(); // Guardamos el tipo seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tipoSeleccionado = null; // Si no selecciona nada
            }
        });

        ArrayList<String> listaSpinner = new ArrayList<>();
        listaSpinner.add("Reporte"); // Elementos del desplegable de la interfaz de soporte
        listaSpinner.add("Sugerencia");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSpinner);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        textoTipo = findViewById(R.id.soporte_texto_tipo);
        textoContenido = findViewById(R.id.soporte_texto);

        Button enviarButton = findViewById(R.id.enviar_button);
        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos los datos de los campos de texto
                String tipoErrorConsulta = textoTipo.getText().toString().trim();
                String contenidoMensaje = textoContenido.getText().toString().trim();

                if (tipoSeleccionado == null || tipoErrorConsulta.isEmpty() || contenidoMensaje.isEmpty()) {
                    Toast.makeText(SoporteActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Guardar el mensaje en la base de datos
                    boolean isInserted = databaseHelper.insertMessage(tipoSeleccionado, tipoErrorConsulta, contenidoMensaje);

                    if (isInserted) {
                        Toast.makeText(SoporteActivity.this, "Mensaje guardado correctamente", Toast.LENGTH_SHORT).show();
                        // Limpiar campos después de guardar
                        textoTipo.setText("");
                        textoContenido.setText("");
                    } else {
                        Toast.makeText(SoporteActivity.this, "Error al guardar el mensaje", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
