package com.rooted.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.controller.HuertoController;
import com.rooted.controller.PlantaController;

public class PlantaActivity extends AppCompatActivity {
    private PlantaController plantaController;
    private TextView nombrePlantaTextView, fechaPlantacionTextView, fechaRiegoTextView, fechaRecogidaTextView;
    int plantaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);

        // Inicializar el controlador
        plantaController = new PlantaController(this);

        // Referencias a los elementos de la interfaz
        nombrePlantaTextView = findViewById(R.id.nombre_planta);
        fechaPlantacionTextView = findViewById(R.id.fecha_plantacion);
        fechaRiegoTextView = findViewById(R.id.fecha_riego);
        fechaRecogidaTextView = findViewById(R.id.fecha_recogida);

        // Obtener datos enviados desde la actividad anterior
        Intent intent = getIntent();
        String nombrePlanta = intent.getStringExtra("nombrePlanta");
        int huertoId = intent.getIntExtra("huertoId", -1);
        String fechaPlantacion = intent.getStringExtra("fechaPlantacion");
        plantaId = intent.getIntExtra("plantaId", -1);

        // Configurar el nombre de la planta
        nombrePlantaTextView.setText(nombrePlanta);

        // Mostrar la fecha de plantación
        fechaPlantacionTextView.setText("Fecha de plantación: " + fechaPlantacion);

        // Calcular y mostrar la fecha de riego y de recogida
        int tiempoRiego = plantaController.obtenerTiempoRiego(nombrePlanta);
        int tiempoCrecimiento = plantaController.obtenerTiempoCrecimiento(nombrePlanta);

        // Suponiendo que las fechas se guardan en formato `yyyy-MM-dd`
        String fechaRiego = calcularFechaProxima(fechaPlantacion, tiempoRiego);
        String fechaRecogida = calcularFechaProxima(fechaPlantacion, tiempoCrecimiento);

        fechaRiegoTextView.setText("Próximo riego: " + fechaRiego);
        fechaRecogidaTextView.setText("Fecha de recogida: " + fechaRecogida);

        // Configurar botón para borrar huerto
        Button eliminarHuertoButton = findViewById(R.id.eliminar_planta);
        eliminarHuertoButton.setOnClickListener(v -> eliminarPlanta());

        // Configurar botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_atras_button);
        volverMenuButton.setOnClickListener(v -> finish());
    }

    private String calcularFechaProxima(String fechaBase, int dias) {
        // Convierte la fecha base a un objeto LocalDate, suma los días y devuelve la nueva fecha
        java.time.LocalDate fecha = java.time.LocalDate.parse(fechaBase);
        java.time.LocalDate nuevaFecha = fecha.plusDays(dias);
        return nuevaFecha.toString(); // Formato de salida "yyyy-MM-dd"
    }

    private void eliminarPlanta() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar esta planta? Esta acción no se puede deshacer.")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = plantaController.eliminarPlanta(plantaId);
                        if (isDeleted) {
                            Toast.makeText(PlantaActivity.this, "Planta eliminada exitosamente", Toast.LENGTH_SHORT).show();
                            // Devolver el resultado a la actividad anterior
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("plantaEliminada", nombrePlantaTextView.getText().toString());
                            setResult(RESULT_OK, resultIntent);
                            finish(); // Finalizar la actividad actual
                        } else {
                            Toast.makeText(PlantaActivity.this, "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}
