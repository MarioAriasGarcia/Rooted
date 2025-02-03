package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.controller.SoporteController;
import com.rooted.R;

import java.util.List;

public class SoporteAdminActivity extends AppCompatActivity {

    private LinearLayout contenedorMensajes;
    private int userId;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporte_admin);

        userId = getIntent().getIntExtra("user_id", -1);
        nombreUsuario = getIntent().getStringExtra("username");

        contenedorMensajes = findViewById(R.id.contenedor_mensajes);

        // Cargar mensajes desde la base de datos
        cargarMensajes();

        // Configuración del botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(SoporteAdminActivity.this, MainActivity.class);
            intent.putExtra("user_id", userId);
            intent.putExtra("username", nombreUsuario);
            startActivity(intent);
            finish();
        });
    }

    private void cargarMensajes() {
        SoporteController soporteController = new SoporteController(this);

        try {
            List<String[]> mensajes = soporteController.obtenerTodosLosMensajes();

            if (mensajes != null && !mensajes.isEmpty()) {
                for (String[] mensaje : mensajes) {
                    agregarMensajeAlLayout(mensaje);
                }
            } else {
                Toast.makeText(this, "No hay mensajes almacenados.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al cargar los mensajes.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void agregarMensajeAlLayout(String[] mensaje) {
        TextView mensajeView = new TextView(this);
        mensajeView.setText("📌 Tipo: " + mensaje[0] + "\n📝 Título: " + mensaje[2] +
                "\n📄 Descripción: " + mensaje[1] + "\n👤 Autor: " + mensaje[3] + "\n\uD83D\uDCC5 Fecha: " + mensaje[4]);
        mensajeView.setTextSize(18);
        mensajeView.setPadding(20, 20, 20, 20);
        mensajeView.setBackgroundResource(R.drawable.rounded_rectangle);
        mensajeView.setTextColor(getResources().getColor(R.color.black));

        // Margen entre mensajes
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);
        mensajeView.setLayoutParams(params);

        // Agregar al contenedor
        contenedorMensajes.addView(mensajeView);
    }
}
