package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

import com.rooted.R;

public class TutorialesActivity extends AppCompatActivity {
    private boolean isPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales);

        int userId = getIntent().getIntExtra("user_id", -1);
        String nombreUsuario = getIntent().getStringExtra("username");

        VideoView videoView = findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.prueba2;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    videoView.start();
                    isPlaying = true;
                } else {
                    videoView.pause();
                    isPlaying = false;
                }
            }
        });

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
    }
}
