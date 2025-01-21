package com.rooted.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rooted.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MensajeDAO {
    private DatabaseHelper databaseHelper;

    public MensajeDAO(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    // Método para insertar un mensaje
    public boolean insertMensaje(Mensaje mensaje) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("COLUMN_MESSAGE_TYPE", mensaje.getType());
        values.put("COLUMN_MESSAGE_SUBTYPE", mensaje.getSubtype());
        values.put("COLUMN_MESSAGE_CONTENT", mensaje.getContent());
        //values.put("FECHA", mensaje.getFecha().getTime()); // Guardar fecha como timestamp

        long result = db.insert("TABLE_MESSAGES", null, values);
        db.close();
        return result != -1;
    }

    // Método para obtener todos los mensajes
    public List<Mensaje> getAllMensajes() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<Mensaje> mensajes = new ArrayList<>();

        Cursor cursor = db.query("TABLE_MESSAGES", null, null, null, null, null, "fecha DESC");
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("COLUMN_MESSAGE_TYPE"));
                String subtype = cursor.getString(cursor.getColumnIndexOrThrow("COLUMN_MESSAGE_SUBTYPE"));
                String content = cursor.getString(cursor.getColumnIndexOrThrow("COLUMN_MESSAGE_CONTENT"));
                long fechaMillis = cursor.getLong(cursor.getColumnIndexOrThrow("FECHA"));

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
        int result = db.delete("TABLE_MESSAGES", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
