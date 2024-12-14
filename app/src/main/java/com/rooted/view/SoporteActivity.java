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
import com.rooted.ui.theme.MainActivity;

import java.util.ArrayList;

public class SoporteActivity extends AppCompatActivity {

    private EditText textoTipo, textoContenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporte);

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
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(SoporteActivity.this, "Selected Item: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            ArrayList<String> listaSpinner = new ArrayList<>();
            listaSpinner.add("Reporte");          //elementos del desplegable de la interfaz de soporte
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
                String tipoErrorConsulta = textoTipo.getText().toString().trim();
                String contenidoMensaje = textoContenido.getText().toString().trim();
                if(tipoErrorConsulta.isEmpty() || contenidoMensaje.isEmpty()){
                    Toast.makeText(SoporteActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SoporteActivity.this, "AQUI VA EL ENVIO", Toast.LENGTH_SHORT).show();
                }

                // Lógica futura para manejar el envío del soporte a la base de datos

            }
        });
    }
}
