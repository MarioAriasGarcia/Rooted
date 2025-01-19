package com.rooted.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.database.DatabaseHelper;

public class GestionarHuertosActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_huertos);

        // Configurar base de datos
        databaseHelper = new DatabaseHelper(this);

        // Recuperar username y userId del Intent
        String username = getIntent().getStringExtra("username");
        int userId = getIntent().getIntExtra("user_id", -1);

        // Verifica si userId o username son inválidos
        if (username == null) {
            Toast.makeText(this, "Error: No se pudo recuperar los datos del usuario por user", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }else if(userId == -1){
            Toast.makeText(this, "Error: No se pudo recuperar los datos del usuario por id", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Configurar el botón para añadir un nuevo huerto
        Button añadirHuertoButton = findViewById(R.id.añadir_huerto_1);
        añadirHuertoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionarHuertosActivity.this, AddHuertoActivity.class);
                intent.putExtra("user_id", userId); // Pasa el userId
                startActivity(intent);
                finish();
            }
        });

        // Mostrar huertos del usuario
        mostrarHuertosUsuario(userId);

        // Configurar botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionarHuertosActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                finish();
            }
        });
    }


    /**
     * Obtiene y muestra los huertos asociados a un usuario.
     *
     * @param userId ID del usuario
     */
    private void mostrarHuertosUsuario(int userId) {
        LinearLayout listaHuertosLayout = findViewById(R.id.lista_huertos_layout);

        Cursor huertosCursor = databaseHelper.getHuertosByUserId(userId);

        if (huertosCursor != null && huertosCursor.moveToFirst()) {
            do {
                String nombreHuerto = huertosCursor.getString(huertosCursor.getColumnIndexOrThrow("nombre"));

                Button huertoButton = new Button(this);
                huertoButton.setText(nombreHuerto);
                huertoButton.setTextSize(18);
                huertoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Acción para ir al detalle del huerto
                        Intent intent = new Intent(GestionarHuertosActivity.this, DetalleHuertoActivity.class);
                        intent.putExtra("nombre", nombreHuerto);
                        startActivity(intent);
                    }
                });

                listaHuertosLayout.addView(huertoButton);
            } while (huertosCursor.moveToNext());
            huertosCursor.close();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int userId = getIntent().getIntExtra("user_id", -1);
        if (userId != -1) {
            mostrarHuertosUsuario(userId); // Refresca la lista
        }
    }

}
