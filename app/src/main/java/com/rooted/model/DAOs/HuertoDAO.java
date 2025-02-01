package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rooted.database.DatabaseHelper;
import com.rooted.model.entities.Huerto;

import java.util.ArrayList;
import java.util.List;

public class HuertoDAO {
    private DatabaseHelper databaseHelper;

    public HuertoDAO(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    // Insertar un nuevo huerto
    public boolean insertHuerto(String nombre, int size, int userId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("size", size);
        values.put("user_id", userId);

        long result = db.insert("huertos", null, values);
        return result != -1; // Devuelve true si se insertó correctamente
    }

    // Obtener huertos por el ID de usuario
    public List<Huerto> getHuertosByUserId(int userId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<Huerto> huertos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM huertos WHERE user_id = ?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                int size = cursor.getInt(cursor.getColumnIndexOrThrow("size"));

                huertos.add(new Huerto(id, nombre, size));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return huertos;
    }

    // Obtener un huerto por su ID
    public Huerto getHuertoById(int huertoId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Huerto huerto = null;

        // Consultar el huerto por su ID
        Cursor cursor = db.rawQuery("SELECT * FROM huertos WHERE id = ?", new String[]{String.valueOf(huertoId)});

        if (cursor != null && cursor.moveToFirst()) {
            // Extraer datos del cursor
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            int size = cursor.getInt(cursor.getColumnIndexOrThrow("size"));

            // Crear instancia del huerto
            huerto = new Huerto(huertoId, nombre, size);
            cursor.close();
        }

        return huerto; // Devuelve el huerto o null si no se encontró
    }

    public boolean eliminarHuerto(int huertoId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsDeleted = db.delete("huertos", "id = ?", new String[]{String.valueOf(huertoId)});
        return rowsDeleted > 0; // Devuelve true si se eliminó al menos una fila
    }


}
