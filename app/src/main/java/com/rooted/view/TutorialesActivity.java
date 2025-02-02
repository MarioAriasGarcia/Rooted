package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import com.rooted.R;

import java.util.ArrayList;

public class TutorialesActivity extends AppCompatActivity {
    private boolean isPlaying = false;
    private String videoSeleccionado = "prueba";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales);

        int userId = getIntent().getIntExtra("user_id", -1);
        String nombreUsuario = getIntent().getStringExtra("username");

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialesActivity.this, MainActivity.class);
                intent.putExtra("user_id", userId); // Pasar el user_id a la siguiente actividad
                intent.putExtra("username", nombreUsuario); // Pasar el nombre de usuario a la siguiente actividad
                startActivity(intent);
                finish();
            }
        });

        Button viewButton = findViewById(R.id.view_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int videoResId = getResources().getIdentifier(videoSeleccionado, "raw", getPackageName());
                watchVideo(videoResId);
            }
        });

        configurarSpinner();
    }

    private void configurarSpinner(){
        Spinner spinner = findViewById(R.id.spinner_videos);
        ArrayList<String> listaSpinner = new ArrayList<>();
        listaSpinner.add("prueba");
        listaSpinner.add("prueba2");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaSpinner);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                videoSeleccionado = parent.getItemAtPosition(position).toString(); // Guardar tipo seleccionado
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                videoSeleccionado = "prueba";
            }
        });
    }
    private void watchVideo(int videoResId){
        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    //para añadir tutoriales meter los nombres en un array list para configuarar el spinner, luego añadir al array list el nombre del video nuevo
}
