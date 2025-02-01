package com.rooted.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.HuertoController;
import com.rooted.controller.PlantaController;
import com.rooted.model.entities.Planta;

import java.util.List;

public class DetalleHuertoActivity extends AppCompatActivity {
    private PlantaController plantaController;
    private HuertoController huertoController;
    private GridLayout listaPlantasLayout;
    private TextView mensajeSinPlantas;

    private int huertoId;

    // Crear el ActivityResultLauncher para manejar el resultado de añadir una planta
    private final ActivityResultLauncher<Intent> addPlantaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<androidx.activity.result.ActivityResult>() {
                @Override
                public void onActivityResult(androidx.activity.result.ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String plantaNombre = result.getData().getStringExtra("plantaNombre");
                        if (plantaNombre != null) {
                            addPlantaButton(plantaNombre);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_huerto);

        // Obtener datos pasados desde la actividad anterior
        Intent intent = getIntent();
        String nombreHuerto = intent.getStringExtra("nombre");
        int sizeHuerto = intent.getIntExtra("size", -1); // Usa -1 como valor por defecto si no se pasa "size"
        huertoId = intent.getIntExtra("huertoId", -1);

        // Validar datos
        if (nombreHuerto == null || sizeHuerto == -1) {
            Toast.makeText(this, "Datos del huerto no disponibles", Toast.LENGTH_SHORT).show();
            finish(); // Finaliza la actividad si los datos son inválidos
            return;
        }

        // Inicializar el controlador
        plantaController = new PlantaController(this);

        // Referencias a los elementos de la interfaz
        TextView nombreTextView = findViewById(R.id.nombre_huerto);
        TextView sizeTextView = findViewById(R.id.size_huerto);
        listaPlantasLayout = findViewById(R.id.lista_plantas_layout);

        mensajeSinPlantas = findViewById(R.id.mensaje_sin_plantas);
        if (mensajeSinPlantas == null) {
            mensajeSinPlantas = new TextView(this);
            mensajeSinPlantas.setText("No tienes plantas registradas");
            mensajeSinPlantas.setTextSize(16);
            mensajeSinPlantas.setPadding(16, 16, 16, 16);
            listaPlantasLayout.addView(mensajeSinPlantas);
        }



        // Configurar los textos
        nombreTextView.setText(nombreHuerto);
        sizeTextView.setText("Tamaño del huerto: " + sizeHuerto + " m²");

        // Configurar botón para añadir planta
        Button añadirPlantaButton = findViewById(R.id.añadir_planta);
        añadirPlantaButton.setOnClickListener(v -> {
            Intent plantaIntent = new Intent(this, AddPlantaActivity.class);
            plantaIntent.putExtra("huertoId", huertoId);
            // Lanzar la actividad para obtener el resultado
            addPlantaLauncher.launch(plantaIntent);
        });

        // Configurar botón para borrar huerto
        Button eliminarHuertoButton = findViewById(R.id.eliminar_huerto);
        eliminarHuertoButton.setOnClickListener(v -> eliminarHuerto());

        // Configurar botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_atras_button);
        volverMenuButton.setOnClickListener(v -> finish());

        // Mostrar plantas registradas
        mostrarPlantasUsuario(huertoId);
    }

    // Método para mostrar las plantas del huerto
    private void mostrarPlantasUsuario(int huertoId) {
        List<Planta> plantas = plantaController.obtenerPlantasPorHuerto(huertoId);

        if (plantas.isEmpty()) {
            mensajeSinPlantas.setVisibility(View.VISIBLE); // Mostrar el mensaje si no hay plantas
        } else {
            mensajeSinPlantas.setVisibility(View.GONE); // Ocultar el mensaje si hay plantas
//            if (mensajeSinPlantas != null) {
//                listaPlantasLayout.removeView(mensajeSinPlantas);
//                mensajeSinPlantas = null;
//            }

            for (Planta planta : plantas) {
                addPlantaButton(planta.getNombre());
            }
        }
    }


    // Método para agregar un botón con la planta añadida
    private void addPlantaButton(String plantaNombre) {
        // Eliminar mensaje si existe
        if (mensajeSinPlantas != null) {
            listaPlantasLayout.removeView(mensajeSinPlantas);
            mensajeSinPlantas = null;
        }

        // Crear botón dinámico
        ImageButton plantaButton = new ImageButton(this);
        int imageResId = obtenerImagenPlanta(plantaNombre);
        plantaButton.setImageResource(imageResId);
        plantaButton.setBackground(null);
        plantaButton.setScaleType(ImageView.ScaleType.CENTER_CROP);

        int sizeInDp = 100;
        int sizeInPx = (int) (sizeInDp * getResources().getDisplayMetrics().density);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = sizeInPx;
        params.height = sizeInPx;
        params.setMargins(16, 16, 16, 16);

        plantaButton.setLayoutParams(params);

        plantaButton.setOnClickListener(v -> {
            Toast.makeText(this, "Has seleccionado " + plantaNombre, Toast.LENGTH_SHORT).show();
        });

        listaPlantasLayout.addView(plantaButton);
    }




    /**
     * Método para obtener la imagen correspondiente a la planta.
     */
    private int obtenerImagenPlanta(String plantaNombre) {
        switch (plantaNombre.toLowerCase()) {
            case "tomate":
                return R.drawable.tomate;
            case "lechuga":
                return R.drawable.lechuga;
            case "garbanzos":
                return R.drawable.garbanzos;
            case "pimientos":
                return R.drawable.pimiento;
            case "cebolla":
                return R.drawable.cebolla;
            case "lentejas":
                return R.drawable.lentejas;
            case "frijoles":
                return R.drawable.frijoles;
            case "habas":
                return R.drawable.habas;
            case "guisantes":
                return R.drawable.guisantes;
            case "patata":
                return R.drawable.patata;
            case "yuca":
                return R.drawable.yuca;
            case "pepino":
                return R.drawable.pepino;
            case "calabaza":
                return R.drawable.calabaza;
            default:
                return R.drawable.default_fruta; // Imagen por defecto si no hay coincidencia
        }

    }


    private void eliminarHuerto() {
        huertoController = new HuertoController(this);
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este huerto? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = huertoController.eliminarHuerto(huertoId);
                        if (isDeleted) {
                            Toast.makeText(DetalleHuertoActivity.this, "Huerto eliminado exitosamente", Toast.LENGTH_SHORT).show();
                            finish(); // Finalizar la actividad actual
                        } else {
                            Toast.makeText(DetalleHuertoActivity.this, "Error al eliminar el huerto", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
