package com.rooted.view;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.GaleriaController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class GaleriaActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_IMAGE = 1;
    private int plantaId;
    private String nombrePlanta;
    private LinearLayout contenedorImagenes;
    private GaleriaController galeriaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        plantaId = getIntent().getIntExtra("planta_id", -1);
        nombrePlanta = getIntent().getStringExtra("nombrePlanta");

        System.out.println("La planta " + nombrePlanta + " con su id " + plantaId);

        galeriaController = new GaleriaController(this);

        configurarUI();
        cargarImagenes();
    }

    private void configurarUI() {
        TextView nombrePlantaTextView = findViewById(R.id.nombre_planta);
        nombrePlantaTextView.setText(nombrePlanta);

        contenedorImagenes = findViewById(R.id.contenedor_imagenes);

        Button seleccionarImagenButton = findViewById(R.id.seleccionar_imagen_button);
        seleccionarImagenButton.setOnClickListener(v -> abrirGaleria());

        Button volverMenuButton = findViewById(R.id.volver_atras_button);
        volverMenuButton.setOnClickListener(v -> finish());
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                Uri localUri = copiarImagenLocal(selectedImageUri);
                if (localUri != null) {
                    galeriaController.agregarImagen(plantaId, localUri.toString());
                    agregarImagen(localUri.toString());
                }
            }
        }
    }

    private void cargarImagenes() {
        contenedorImagenes.removeAllViews();  // Limpia imágenes anteriores
        List<String> imagenes = galeriaController.obtenerImagenesPorPlanta(plantaId); // Ahora filtra por plantaId

        for (String uri : imagenes) {
            agregarImagen(uri);
        }
    }


    private void agregarImagen(String uriString) {
        View itemView = getLayoutInflater().inflate(R.layout.item_imagen, contenedorImagenes, false);

        ImageView imagenView = itemView.findViewById(R.id.imagen_item);
        Uri uri = Uri.parse(uriString);
        imagenView.setImageURI(uri); // Ahora usas la URI local

        Button eliminarButton = itemView.findViewById(R.id.eliminar_imagen_button);
        eliminarButton.setOnClickListener(v -> mostrarConfirmacionEliminar(itemView, uriString));

        contenedorImagenes.addView(itemView);
    }

    private void mostrarConfirmacionEliminar(View itemView, String uriString) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Eliminar esta imagen?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    galeriaController.eliminarImagen(uriString);
                    contenedorImagenes.removeView(itemView);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private Uri copiarImagenLocal(Uri uri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "imagen_" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Rooted");

            Uri nuevaUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (nuevaUri == null) return null;

            OutputStream outputStream = contentResolver.openOutputStream(nuevaUri);
            if (outputStream != null && inputStream != null) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
                inputStream.close();
            }

            return nuevaUri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
