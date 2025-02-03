package com.rooted.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.rooted.R;
import com.rooted.controller.PlantaController;
import com.rooted.notifications.NotificacionReceiver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class PlantaActivity extends AppCompatActivity {
    private PlantaController plantaController;
    private TextView nombrePlantaTextView, fechaPlantacionTextView, fechaRiegoTextView, fechaRecogidaTextView;
    int plantaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planta);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }


        crearCanalNotificacion(); // Crear el canal si no existe


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

        String fechaRiego = calcularFechaProxima(fechaPlantacion, tiempoRiego);
        String fechaRecogida = calcularFechaProxima(fechaPlantacion, tiempoCrecimiento);

        fechaRiegoTextView.setText("Próximo riego: " + fechaRiego);
        fechaRecogidaTextView.setText("Fecha de recogida: " + fechaRecogida);

        // Programar notificaciones
        programarNotificacion(
                this,
                "Riego",
                "Es hora de regar " + nombrePlanta,
                fechaRiego,
                101
        );

        programarNotificacion(
                this,
                "Cosecha",
                "¡Ya puedes recoger " + nombrePlanta + "!",
                fechaRecogida,
                102
        );


        // Dentro de onCreate()
        Button botonAñadirFoto = findViewById(R.id.añadir_foto);
        botonAñadirFoto.setOnClickListener(v -> {
            Intent intentGaleria = new Intent(PlantaActivity.this, GaleriaActivity.class);
            System.out.println("La planta " + nombrePlanta + " con su id " + plantaId);
            intentGaleria.putExtra("planta_id", plantaId);
            intentGaleria.putExtra("nombrePlanta", nombrePlanta);
            startActivity(intentGaleria);
        });

        // Configurar botón para borrar huerto
        Button eliminarHuertoButton = findViewById(R.id.eliminar_planta);
        eliminarHuertoButton.setOnClickListener(v -> eliminarPlanta());

        // Configurar botón para volver al menú
        Button volverMenuButton = findViewById(R.id.volver_atras_button);
        volverMenuButton.setOnClickListener(v -> finish());
    }

    private void enviarNotificacionPrueba() {
        Log.d("Notificacion", "Método enviarNotificacionPrueba llamado");
        //crearCanalNotificacion(); // Crear el canal si no existe

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Configurar la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mi_canal")
                .setSmallIcon(R.drawable.ic_logo) // Reemplaza con tu ícono
                .setContentTitle("Notificación de Prueba")
                .setContentText("Esto es una notificación de prueba al pulsar el botón.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build()); // ID único para esta notificación
            Log.d("Notificacion", "Notificación enviada" + builder);
        } else {
            Log.e("Notificacion", "NotificationManager es nulo");
        }
    }



    private String calcularFechaProxima(String fechaBase, int dias) {
        // Formato de la fecha de entrada y salida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Convertir la fecha base a LocalDate
        LocalDate fecha = LocalDate.parse(fechaBase, formatter);

        // Sumar los días
        LocalDate nuevaFecha = fecha.plusDays(dias);

        // Retornar la nueva fecha en formato "dd-MM-yyyy"
        return nuevaFecha.format(formatter);
    }

    @SuppressLint("ScheduleExactAlarm")
    private void programarNotificacion(Context context, String titulo, String mensaje, String fechaNotificacion, int requestCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse(fechaNotificacion, formatter);

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(fecha.getYear(), fecha.getMonthValue() - 1, fecha.getDayOfMonth(), 9, 0, 0); // Notificación a las 9 AM

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY)); // Hora actual
        calendar.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE) + 1); // Notificación 1 minuto después
        calendar.set(Calendar.SECOND, 0);


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificacionReceiver.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("mensaje", mensaje);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        if (alarmManager != null) {
            // Programar alarma exacta
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        verificarPermisosNotificacion();
    }

    private void verificarPermisosNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(this, "Permiso de notificaciones exactas requerido", Toast.LENGTH_SHORT).show();
                // Redirigir a la configuración para que el usuario otorgue el permiso
                Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
    }

    private void crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "mi_canal";
            CharSequence nombre = "Canal de Notificaciones";
            String descripcion = "Canal para las notificaciones de la app";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, nombre, importancia);
            channel.setDescription(descripcion);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
