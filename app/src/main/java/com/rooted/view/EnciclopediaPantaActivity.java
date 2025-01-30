package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;

public class EnciclopediaPantaActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales);

        Button volverMenuButton = findViewById(R.id.volver_enciclopedia_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.rooted.view.EnciclopediaPantaActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}



