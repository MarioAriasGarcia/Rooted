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

}
