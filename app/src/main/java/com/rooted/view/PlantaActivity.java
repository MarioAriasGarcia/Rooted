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
import com.rooted.controller.PlantaController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlantaActivity extends AppCompatActivity {
    private PlantaController plantaController;
    private TextView nombrePlantaTextView, fechaPlantacionTextView, fechaRiegoTextView, fechaRecogidaTextView, fechaHoyTextView, tocaRegarTextView, tocaRecogerTextView;
    private Button botonRegadoHoy;
    private Button botonRecogidoHoy;
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
        fechaHoyTextView = findViewById(R.id.fecha_hoy);
        tocaRegarTextView = findViewById(R.id.toca_regar);
        tocaRecogerTextView = findViewById(R.id.toca_recoger);
        botonRegadoHoy = findViewById(R.id.boton_regado_hoy);
        botonRecogidoHoy = findViewById(R.id.boton_recogido_hoy);

        // Obtener datos enviados desde la actividad anterior
        Intent intent = getIntent();
        String nombrePlanta = intent.getStringExtra("nombrePlanta");
        String fechaPlantacion = intent.getStringExtra("fechaPlantacion");
        plantaId = intent.getIntExtra("plantaId", -1);

        // Configurar el nombre de la planta
        nombrePlantaTextView.setText(nombrePlanta);

        // Mostrar la fecha de plantación
        fechaPlantacionTextView.setText("Fecha de plantación: " + fechaPlantacion);

        int tiempoCrecimiento = plantaController.obtenerTiempoCrecimiento(nombrePlanta);
        int tiempoRiego = plantaController.obtenerTiempoRiego(nombrePlanta);

        String fechaRecogida = calcularFechaProxima(fechaPlantacion, tiempoCrecimiento);

        String primeraFechaRiego = calcularFechaProxima(fechaPlantacion, tiempoRiego);
        // Obtener datos de la base de datos
        if(plantaController.getSiguienteRiego(plantaId) == null){
            plantaController.actualizarSiguienteRiego(plantaId, primeraFechaRiego);
        }
        String fechaRiego = plantaController.getSiguienteRiego(plantaId);

        fechaRiegoTextView.setText("Próximo riego: " + fechaRiego);
        fechaRecogidaTextView.setText("Fecha de recogida: " + fechaRecogida);

        // Mostrar la fecha actual
        String fechaHoy = obtenerFechaHoy();
        fechaHoyTextView.setText("Fecha de hoy: " + fechaHoy);

        // Verificar si hoy es día de riego o de recogida
        if (fechaHoy.equals(fechaRiego)) {
            tocaRegarTextView.setText("Es hora de regar!!");
        } else {
            tocaRegarTextView.setText("");
        }

        if (fechaHoy.equals(fechaRecogida)) {
            tocaRecogerTextView.setText("Es hora de recoger!!");
        } else {
            tocaRecogerTextView.setText("");
        }

        // Configurar botón "Ya he regado hoy"
        botonRegadoHoy.setOnClickListener(v -> {
            if (fechaHoy.equals(fechaRiego)) {
                botonRegadoHoy.setText("Ya se ha regado");
                String nuevaFechaRiego = calcularFechaProxima(fechaRiego, plantaController.obtenerTiempoRiego(nombrePlantaTextView.getText().toString()));
                plantaController.actualizarSiguienteRiego(plantaId, nuevaFechaRiego);
                fechaRiegoTextView.setText("Próximo riego: " + nuevaFechaRiego);
                tocaRegarTextView.setText("Ya has regado hoy, vuelve a hacerlo el " + nuevaFechaRiego);
                botonRegadoHoy.setEnabled(false);
                Toast.makeText(this, "Fecha de riego actualizada", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Hoy no es dia de riego", Toast.LENGTH_SHORT).show();
            }
        });


        botonRecogidoHoy.setOnClickListener(v -> recogerPlanta());

        // Configurar botón para ver galería
        Button botonAñadirFoto = findViewById(R.id.añadir_foto);
        botonAñadirFoto.setOnClickListener(v -> {
            Intent intentGaleria = new Intent(PlantaActivity.this, GaleriaActivity.class);
            intentGaleria.putExtra("planta_id", plantaId);
            intentGaleria.putExtra("nombrePlanta", nombrePlanta);
            startActivity(intentGaleria);
        });

        // Configurar botón para borrar planta
        Button eliminarHuertoButton = findViewById(R.id.eliminar_planta);
        eliminarHuertoButton.setOnClickListener(v -> eliminarPlanta());

        // Configurar botón para volver
        Button volverMenuButton = findViewById(R.id.volver_atras_button);
        volverMenuButton.setOnClickListener(v -> finish());
    }

    private String calcularFechaProxima(String fechaBase, int dias) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(fechaBase, formatter);
        LocalDate nuevaFecha = fecha.plusDays(dias);
        return nuevaFecha.format(formatter);
    }

    private String obtenerFechaHoy() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.now().format(formatter);
        //return "06-05-2025";
    }

    private void recogerPlanta() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar recogida")
                .setMessage("¿Estás seguro de que quieres recoger la planta? Esta acción no se puede deshacer.")
                .setPositiveButton("Recoger", (dialog, which) -> {
                    botonRecogidoHoy.setText("Ya se ha recogido");
                    plantaController.marcarComoRecogida(plantaId);
                    fechaRiegoTextView.setText("YA ESTÁ RECOGIDA");
                    fechaRecogidaTextView.setText("YA ESTÁ RECOGIDA");
                    tocaRegarTextView.setText("Planta recogida");
                    tocaRecogerTextView.setText("");
                    botonRegadoHoy.setEnabled(false);
                    botonRecogidoHoy.setEnabled(false);
                    Toast.makeText(PlantaActivity.this, "Planta recogida exitosamente", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
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
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("plantaEliminada", nombrePlantaTextView.getText().toString());
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(PlantaActivity.this, "Error al eliminar la planta", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Cargar la fecha de hoy
        String fechaHoy = obtenerFechaHoy();
        fechaHoyTextView.setText("Fecha de hoy: " + fechaHoy);

        // Obtener información de la planta desde la base de datos
        String fechaRiego = plantaController.getSiguienteRiego(plantaId);
        String fechaRecogida = calcularFechaProxima(fechaPlantacionTextView.getText().toString().replace("Fecha de plantación: ", ""), plantaController.obtenerTiempoCrecimiento(nombrePlantaTextView.getText().toString()));

        // Verificar si la planta ya fue recogida
        if (plantaController.isRecogida(plantaId)) {
            fechaRiegoTextView.setText("YA ESTÁ RECOGIDA");
            fechaRecogidaTextView.setText("YA ESTÁ RECOGIDA");
            tocaRegarTextView.setText("");
            tocaRecogerTextView.setText("");
            botonRegadoHoy.setEnabled(false);
            botonRecogidoHoy.setEnabled(false);
            return;
        }

        // Mostrar la próxima fecha de riego
        fechaRiegoTextView.setText("Próximo riego: " + fechaRiego);
        fechaRecogidaTextView.setText("Fecha de recogida: " + fechaRecogida);

        // Verificar si hoy es el día de riego
        if (fechaHoy.equals(fechaRiego)) {
            botonRegadoHoy.setText("Regar planta");
            tocaRegarTextView.setText("Es hora de regar!!");
            botonRegadoHoy.setEnabled(true);
        } else {
            tocaRegarTextView.setText("");
            botonRegadoHoy.setText("Hoy no es dia de riego");
            botonRegadoHoy.setEnabled(false);
        }

        // Verificar si hoy es el día de recogida
        if (fechaHoy.equals(fechaRecogida)) {
            botonRecogidoHoy.setText("Recoger planta");
            tocaRecogerTextView.setText("Es hora de recoger!!");
            botonRecogidoHoy.setEnabled(true);
        } else {
            tocaRecogerTextView.setText("");
            botonRecogidoHoy.setText("Hoy no es dia de cosecha");
            botonRecogidoHoy.setEnabled(false);
        }

        if(esFechaPosterior(fechaHoy, fechaRecogida)){
            mostrarDialogoOlvidoRecogida();
        }

        if (esFechaPosterior(fechaHoy, fechaRiego)) {
            mostrarDialogoOlvidoRiego();
        }

    }

    private boolean esFechaPosterior(String fechaHoy, String fechaRiego) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate hoy = LocalDate.parse(fechaHoy, formatter);
        LocalDate riego = LocalDate.parse(fechaRiego, formatter);
        return hoy.isAfter(riego);
    }

    private void mostrarDialogoOlvidoRiego() {
        new AlertDialog.Builder(this)
                .setTitle("¡Se te ha olvidado regar!")
                .setMessage("La fecha de riego ha pasado. Por favor, riega la planta lo antes posible.")
                .setPositiveButton("Regar ahora", (dialog, which) -> {
                    // Actualizar la fecha de riego a la siguiente posible
                    String nuevaFechaRiego = calcularFechaProxima(obtenerFechaHoy(), plantaController.obtenerTiempoRiego(nombrePlantaTextView.getText().toString()));
                    plantaController.actualizarSiguienteRiego(plantaId, nuevaFechaRiego);
                    fechaRiegoTextView.setText("Próximo riego: " + nuevaFechaRiego);
                    tocaRegarTextView.setText("Ya has regado hoy, vuelve a hacerlo el " + nuevaFechaRiego);
                    botonRegadoHoy.setEnabled(false);
                    Toast.makeText(this, "Fecha de riego actualizada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Más tarde", null)
                .show();
    }

    private void mostrarDialogoOlvidoRecogida() {
        new AlertDialog.Builder(this)
                .setTitle("¡Se te ha olvidado recoger la planta!")
                .setMessage("La fecha de recogida ha pasado. Por favor, recoge la planta lo antes posible.")
                .setPositiveButton("Recoger ahora", (dialog, which) -> {
                    plantaController.marcarComoRecogida(plantaId);
                    fechaRiegoTextView.setText("YA ESTÁ RECOGIDA");
                    fechaRecogidaTextView.setText("YA ESTÁ RECOGIDA");
                    botonRecogidoHoy.setEnabled(false);
                    botonRegadoHoy.setEnabled(false);
                    tocaRegarTextView.setText("Planta recogida");
                    Toast.makeText(this, "Planta recogida", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Más tarde", null)
                .show();
    }



}
