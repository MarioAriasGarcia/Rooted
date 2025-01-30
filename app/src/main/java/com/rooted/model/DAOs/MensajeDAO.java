package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.rooted.database.DatabaseHelper;
import com.rooted.model.entities.Mensaje;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MensajeDAO {
    private DatabaseHelper databaseHelper;

    public MensajeDAO(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    // Método para insertar un mensaje


    public boolean insertMensaje(Mensaje mensaje) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Formatea la fecha como legible antes de guardarla
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(mensaje.getFecha());

        values.put("message_type", mensaje.getType());
        values.put("message_subtype", mensaje.getSubtype());
        values.put("message_content", mensaje.getContent());
        values.put("date", formattedDate); // Guardar la fecha como texto legible

        long result = db.insert("messages", null, values);
        db.close();
        return result != -1;
    }


    // Método para obtener todos los mensajes
    public List<Mensaje> getAllMensajes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<Mensaje> mensajes = new ArrayList<>();

        Cursor cursor = db.query("messages", null, null, null, null, null, "fecha DESC");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("message_type"));
                String subtype = cursor.getString(cursor.getColumnIndexOrThrow("message_subtype"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("message_content"));
                long fechaMillis = cursor.getLong(cursor.getColumnIndexOrThrow("date"));

                mensajes.add(new Mensaje(id, type, subtype, content, new Date(fechaMillis)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return mensajes;
    }

    // Método para eliminar un mensaje por ID
    public boolean deleteMensaje(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int result = db.delete("messages", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
