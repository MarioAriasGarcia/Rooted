package com.rooted.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.rooted.R;
import com.rooted.controller.TutorialesController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TutorialesActivity extends AppCompatActivity {
    private boolean isPlaying = false;
    private TutorialesController tutorialesController;
    private String videoName;
    private List<String> nombres_videos = new ArrayList<>();
    private String videoSeleccionado = "prueba";
    private static final int PICK_VIDEO_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales);

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
                watchVideo();
            }
        });


        configurarSpinner();

        Button addTutorialButton = findViewById(R.id.add_tutorial_button);
        addTutorialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTutorial();
                configurarSpinner();
            }
        });

    }

    private void configurarSpinner(){
        Spinner spinner = findViewById(R.id.spinner_videos);
        ArrayList<String> listaSpinner = new ArrayList<>();
        listaSpinner.add("prueba");
        listaSpinner.add("prueba2");
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
                videoSeleccionado = "prueba";
                videoSeleccionado = nombres_videos.get(1);
            }
        });
    }
    private void watchVideo(){
        VideoView videoView = findViewById(R.id.videoView);
        String uri_String = tutorialesController.obtenerVideo(this.videoSeleccionado);
        Uri uri = Uri.parse(uri_String);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }


        private void addTutorial() {
            // Crear un diálogo para pedir el nombre del video
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nombre del Video");

            // Crear un EditText para que el usuario ingrese el nombre
            final EditText input = new EditText(this);
            input.setHint("Introduce el nombre del video");
            builder.setView(input);

            // Botón para aceptar
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                videoName = input.getText().toString().trim();

                if (videoName.isEmpty()) {
                    Toast.makeText(this, "Debes ingresar un nombre válido", Toast.LENGTH_SHORT).show();
                } else {
                    // Abrir galería para seleccionar el video
                    openGallery();
                }
            });

            // Botón para cancelar
            builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

            builder.show();
        }

        private void openGallery() {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("video/*");
            startActivityForResult(intent, PICK_VIDEO_REQUEST);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_VIDEO_REQUEST && resultCode == RESULT_OK && data != null) {
                Uri selectedVideoUri = data.getData();
                if (selectedVideoUri != null) {
                    saveVideoToInternalStorage(selectedVideoUri);
                }
            }
        }

        private void saveVideoToInternalStorage(Uri videoUri) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(videoUri);

                // Guardar en almacenamiento interno con el nombre proporcionado
                File videoFile = new File(getFilesDir(), videoName + ".mp4");
                OutputStream outputStream = new FileOutputStream(videoFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                // Guardar el video en el controlador y actualizar el Spinner
                tutorialesController.addTutorialC(videoName, videoFile.getAbsolutePath());
                nombres_videos.add(videoName);
                configurarSpinner();

                Toast.makeText(this, "Video guardado correctamente", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar el video", Toast.LENGTH_SHORT).show();
            }
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