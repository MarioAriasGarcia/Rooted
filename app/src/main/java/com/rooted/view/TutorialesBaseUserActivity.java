package com.rooted.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.TutorialesController;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TutorialesBaseUserActivity extends AppCompatActivity {
    private TutorialesController tutorialesController;
    private List<String> nombres_videos = new ArrayList<>();
    private String videoSeleccionado = "Añadir foto planta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales_base_user);

        int userId = getIntent().getIntExtra("user_id", -1);
        String nombreUsuario = getIntent().getStringExtra("username");
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        tutorialesController = new TutorialesController(this);

        setUPList();
        configurarSpinner();

        Button volverMenuButton = findViewById(R.id.volver_menu_button);
        volverMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialesBaseUserActivity.this, MainActivity.class);
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
                watchVideo();
            }
        });
    }

    private void configurarSpinner(){
        Spinner spinner = findViewById(R.id.spinner_videos);
        ArrayList<String> listaSpinner = new ArrayList<>();
        listaSpinner.addAll(this.nombres_videos);

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
                videoSeleccionado = nombres_videos.get(1);
            }
        });
    }
    private void watchVideo(){
        VideoView videoView = findViewById(R.id.videoView2);
        String uri_String = tutorialesController.obtenerVideo(this.videoSeleccionado);
        Uri uri = Uri.parse(uri_String);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    private void setUPList(){
        this.nombres_videos.add("Añadir foto planta");
        this.nombres_videos.add("Añadir huerto");
        this.nombres_videos.add("Añadir planta");
        this.nombres_videos.add("Buscar planta en enciclopedia");
        this.nombres_videos.add("Eliminar huerto");
        this.nombres_videos.add("Enviar mensaje reporte");
        this.nombres_videos.add("Ver info usuario");
        String path1 = "android.resource://" + getPackageName() + "/" + R.raw.add_foto_a_planta;
        String path2 = "android.resource://" + getPackageName() + "/" + R.raw.add_huerto;
        String path3 = "android.resource://" + getPackageName() + "/" + R.raw.add_planta;
        String path4 = "android.resource://" + getPackageName() + "/" + R.raw.eliminar_huerto;
        String path5 = "android.resource://" + getPackageName() + "/" + R.raw.buscar_planta_en_la_enciclopedia;
        String path6 = "android.resource://" + getPackageName() + "/" + R.raw.enviar_mensaje_reporte;
        String path7 = "android.resource://" + getPackageName() + "/" + R.raw.ver_info_usuario;
        tutorialesController.addTutorialC("Añadir foto planta", path1);
        tutorialesController.addTutorialC("Añadir huerto", path2);
        tutorialesController.addTutorialC("Añadir planta", path3);
        tutorialesController.addTutorialC("Eliminar huerto", path4);
        tutorialesController.addTutorialC("Buscar planta en enciclopedia", path5);
        tutorialesController.addTutorialC("Enviar mensaje reporte", path6);
        tutorialesController.addTutorialC("Ver info usuario", path7);
        File internalStorage = getFilesDir();
        File[] files = internalStorage.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".mp4")) {
                    String videoName = file.getName().replace(".mp4", ""); // Eliminar extensión
                    nombres_videos.add(videoName);
                    tutorialesController.addTutorialC(videoName, file.getAbsolutePath());
                }
            }
        }
    }
}