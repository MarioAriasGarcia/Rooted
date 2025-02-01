package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.rooted.R;
import com.rooted.controller.HuertoController;
import com.rooted.model.entities.Huerto;

import java.util.List;

public class GestionarHuertosActivity extends AppCompatActivity {

    private HuertoController huertoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_huertos);

        huertoController = new HuertoController(this);

        String username = getIntent().getStringExtra("username");
        int userId = getIntent().getIntExtra("user_id", -1);

        if (username == null || userId == -1) {
            System.out.println("El nombre de usuario es: " + username + " y mi id es: " + userId);
            Toast.makeText(this, "Error al recuperar datos del usuario", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Button añadirHuertoButton = findViewById(R.id.añadir_huerto_1);
        añadirHuertoButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddHuertoActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        mostrarHuertosUsuario(userId);
    }

    private void mostrarHuertosUsuario(int userId) {
        LinearLayout listaHuertosLayout = findViewById(R.id.lista_huertos_layout);
        listaHuertosLayout.removeAllViews();

        List<Huerto> huertos = huertoController.obtenerHuertosPorUsuario(userId);

        if (huertos.isEmpty()) {
            TextView sinHuertos = new TextView(this);
            sinHuertos.setText("No tienes huertos registrados");
            sinHuertos.setTextSize(18); // Tamaño del texto
            sinHuertos.setAllCaps(false); // Sin mayúsculas
            sinHuertos.setElevation(6); // Sombra para profundidad

            // Crear un objeto LayoutParams con márgenes
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,  // Ancho ajustable
                    LinearLayout.LayoutParams.WRAP_CONTENT   // Alto ajustable
            );

            // Establecer márgenes (en píxeles)
            int margen = 16; // Ejemplo de 16px de margen
            params.setMargins(margen, margen, margen, margen); // Margen para los cuatro lados

            // Aplicar los parámetros al TextView
            sinHuertos.setLayoutParams(params);

            // Agregar el TextView a tu layout
            listaHuertosLayout.addView(sinHuertos);
        }
        else {
            for (Huerto huerto : huertos) {
                Button huertoButton = new Button(this);
                huertoButton.setText(huerto.getNombre());
                huertoButton.setTextColor(ContextCompat.getColor(this, R.color.white)); // Color del texto
                huertoButton.setPadding(20, 20, 20, 20); // Padding interno
                huertoButton.setBackgroundResource(R.drawable.custom_bg_boton); // Fondo personalizado
                huertoButton.setTextSize(18); // Tamaño del texto
                huertoButton.setAllCaps(false); // Sin mayúsculas
                huertoButton.setElevation(6); // Sombra para profundidad

                // Configurar márgenes y ancho personalizado
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) (getResources().getDisplayMetrics().widthPixels * 0.8), // 80% del ancho de la pantalla
                        LinearLayout.LayoutParams.WRAP_CONTENT // Alto del botón ajustado al contenido
                );
                params.setMargins(0, 16, 0, 16); // Márgenes: izquierda, arriba, derecha, abajo
                params.gravity = Gravity.CENTER_HORIZONTAL; // Centrar horizontalmente
                huertoButton.setLayoutParams(params);

                // Configurar la acción al hacer clic en el botón
                huertoButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this, DetalleHuertoActivity.class);
                    intent.putExtra("nombre", huerto.getNombre());
                    intent.putExtra("size", huerto.getSize());
                    intent.putExtra("huertoId", huerto.getId());
                    startActivity(intent);
                });

                listaHuertosLayout.addView(huertoButton); // Añadir botón al layout
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        int userId = getIntent().getIntExtra("user_id", -1);
        if (userId != -1) {
            mostrarHuertosUsuario(userId);
        }
    }
}
