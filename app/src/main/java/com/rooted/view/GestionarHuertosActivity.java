package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.HuertoController;
import com.rooted.model.Huerto;
import com.rooted.ui.theme.MainActivity;

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
            listaHuertosLayout.addView(sinHuertos);
        } else {
            for (Huerto huerto : huertos) {
                Button huertoButton = new Button(this);
                huertoButton.setText(huerto.getNombre());
                huertoButton.setOnClickListener(v -> {
                    Intent intent = new Intent(this, DetalleHuertoActivity.class);
                    intent.putExtra("nombre", huerto.getNombre());
                    intent.putExtra("size", huerto.getSize());
                    startActivity(intent);
                });
                listaHuertosLayout.addView(huertoButton);
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
