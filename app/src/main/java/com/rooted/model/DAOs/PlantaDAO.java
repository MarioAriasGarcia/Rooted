package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rooted.database.DatabaseHelper;
import com.rooted.model.entities.Huerto;
import com.rooted.model.entities.Planta;

import java.util.ArrayList;
import java.util.List;

public class PlantaDAO {
    private DatabaseHelper databaseHelper;

    public PlantaDAO(Context context){
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    public boolean addPlanta(String planta, int huertoId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Asignar valores
        values.put("nombre", planta);
        values.put("huerto_id", huertoId);

        // Insertar en la base de datos
        long result = db.insert("plantas", null, values);
        db.close();
        return result != -1; // Retorna true si la inserción fue exitosa
    }

    public int getTiempoRiego(String nombrePlanta) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        int tiempoRiego = -1; // Valor predeterminado en caso de error o no encontrado
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT " + DatabaseHelper.COLUMN_TIEMPO_RIEGO_DATA +
                            " FROM " + DatabaseHelper.TABLE_PLANTAS_DATA +
                            " WHERE " + DatabaseHelper.COLUMN_PLANTA_NOMBRE_DATA + " = ?",
                    new String[]{nombrePlanta}
            );

            if (cursor != null && cursor.moveToFirst()) {
                tiempoRiego = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIEMPO_RIEGO_DATA));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return tiempoRiego;
    }

    public int getTiempoCrecimiento(String nombrePlanta) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        int tiempoCrecimiento = -1; // Valor predeterminado en caso de error o no encontrado
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(
                    "SELECT " + DatabaseHelper.COLUMN_TIEMPO_CRECIMIENTO_DATA +
                            " FROM " + DatabaseHelper.TABLE_PLANTAS_DATA +
                            " WHERE " + DatabaseHelper.COLUMN_PLANTA_NOMBRE_DATA + " = ?",
                    new String[]{nombrePlanta}
            );

            if (cursor != null && cursor.moveToFirst()) {
                tiempoCrecimiento = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIEMPO_CRECIMIENTO_DATA));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return tiempoCrecimiento;
    }

    public List<Planta> getPlantasByHuertoId(int huertoId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<Planta> plantas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM plantas WHERE huerto_id = ?", new String[]{String.valueOf(huertoId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                int tiempoRiego = cursor.getInt(cursor.getColumnIndexOrThrow("tiempo_riego"));
                int tiempoCrecimiento = cursor.getInt(cursor.getColumnIndexOrThrow("tiempo_crecimiento"));

                plantas.add(new Planta(id, nombre, tiempoRiego, tiempoCrecimiento));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return plantas;
    }

    public int obtenerPlantaIdPorNombreYHuerto(String nombrePlanta, int huertoId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT id FROM plantas WHERE nombre = ? AND huerto_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombrePlanta, String.valueOf(huertoId)});

        int plantaId = -1;
        if (cursor.moveToFirst()) {
            plantaId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return plantaId;
    }

    public boolean eliminarPlanta(int plantaId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsDeleted = db.delete("plantas", "id = ?", new String[]{String.valueOf(plantaId)});
        return rowsDeleted > 0; // Devuelve true si se eliminó al menos una fila
    }

    public long agregarImagen(int plantaId, String uri) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uri", uri);
        values.put("planta_id", plantaId);
        return db.insert("plantas_imagenes", null, values);
    }

    public List<String> obtenerImagenesPorPlanta(int plantaId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<String> uris = new ArrayList<>();
        Cursor cursor = db.query("plantas_imagenes", new String[]{"uri"}, "planta_id = ?",
                new String[]{String.valueOf(plantaId)}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                uris.add(cursor.getString(0));
            }
            cursor.close();
        }
        return uris;
    }


    // Eliminar una imagen específica
    public void eliminarImagen(String uri) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("plantas_imagenes", "uri = ?", new String[]{uri});
    }

    public String getSiguienteRiego(int plantaId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String siguienteRiego = "No disponible";

        String query = "SELECT siguiente_riego FROM plantas WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(plantaId)});

        if (cursor.moveToFirst()) {
            siguienteRiego = cursor.getString(0); // Obtener la fecha de riego
        }

        cursor.close();
        db.close();

        return siguienteRiego;
    }

    public void actualizarSiguienteRiego(int plantaId, String nuevaFechaRiego) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("siguiente_riego", nuevaFechaRiego);

        int rowsUpdated = db.update("plantas", values, "id = ?", new String[]{String.valueOf(plantaId)});

        db.close();
    }

    public void marcarComoRecogida(int plantaId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("esta_recogida", 1); // 1 indica que la planta ha sido recogida

        int rowsUpdated = db.update("plantas", values, "id = ?", new String[]{String.valueOf(plantaId)});

        db.close();
    }

    public void marcarComoRecogida(int plantaId, int estado) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("esta_recogida", estado); // 1 = recogida, 0 = no recogida

        int rowsUpdated = db.update("plantas", values, "id = ?", new String[]{String.valueOf(plantaId)});

        db.close();
    }

    public boolean isRecogida(int plantaId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        // Consulta para obtener el valor de la columna "ESTA_RECOGIDA" (supongo que es un entero)
        String query = "SELECT " + "esta_recogida" + " FROM " + "plantas" + " WHERE " + "id" + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(plantaId)});  // Asume que `plantaId` es el ID de la planta que buscas

        if (cursor != null && cursor.moveToFirst()) {
            // Obtén el valor de la columna "ESTA_RECOGIDA", que es un entero
            int recogida = cursor.getInt(cursor.getColumnIndexOrThrow("esta_recogida"));
            cursor.close(); // Cierra el cursor después de usarlo

            // Devuelve si el valor es 1 (recogida) o 0 (no recogida)
            return recogida == 1;
        } else {
            cursor.close();  // Cierra el cursor si no hay datos
            return false;  // Si no se encuentra la planta o hay un error
        }
    }





    //contar plantas de cada usuario
    public int getPlantasCountByUser(int userId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        int platscounter = 0;

        String query = "SELECT COUNT(*) FROM plantas p " +
                "INNER JOIN huertos h ON p.huerto_id = h.id " +
                "WHERE h.user_id = ?";  // Corrección: usar 'user_id' en lugar de 'usuario_id'

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                platscounter = cursor.getInt(0);
            }
            cursor.close();
        }

        db.close();
        return platscounter;
    }

    //contar fotos de cada usuario
    public int getFotosCountByUser(int userId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        int fotosCounter = 0;

        String query = "SELECT COUNT(*) FROM plantas_imagenes pi " +
                "INNER JOIN plantas p ON pi.planta_id = p.id " +
                "INNER JOIN huertos h ON p.huerto_id = h.id " +
                "WHERE h.user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                fotosCounter = cursor.getInt(0);
            }
            cursor.close();
        }

        db.close();
        return fotosCounter;
    }

}
