package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.database.DatabaseHelper;
import com.rooted.ui.theme.MainActivity;

public class GestionarHuertosActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_huertos);
        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        databaseHelper = new DatabaseHelper(this);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionarHuertosActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button añadirHuerto1 = findViewById(R.id.añadir_huerto_1);
        añadirHuerto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GestionarHuertosActivity.this, AddHuertoActivity.class);
                startActivity(intent);
            }
        });


    }
}