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
    private UsuarioDAO usuarioDAO;

    public MensajeDAO(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
        usuarioDAO = new UsuarioDAO(context);
    }

    // Método para insertar un mensaje
    public boolean insertMensaje(Mensaje mensaje, int userId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Formatea la fecha como legible antes de guardarla
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(mensaje.getFecha());

        values.put("message_type", mensaje.getType());
        values.put("message_subtype", mensaje.getSubtype());
        values.put("message_content", mensaje.getContent());
        values.put("date", formattedDate); // Guardar la fecha como texto legible
        values.put("user_id", userId);

        long result = db.insert("messages", null, values);
        db.close();
        return result != -1;
    }


    // Método para obtener todos los mensajes
    public List<String[]> obtenerTodosLosMensajes() {
        List<String[]> listaMensajes = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT message_type, message_content, message_subtype, user_id, date FROM messages", null);

        if (cursor.moveToFirst()) {
            do {
                String tipo = cursor.getString(0);
                String titulo = cursor.getString(1);
                String descripcion = cursor.getString(2);
                int userId = Integer.parseInt(cursor.getString(3));
                String autor = usuarioDAO.getUserNameByUserId(userId);
                String fechaMensaje = cursor.getString(4);
                listaMensajes.add(new String[]{tipo, titulo, descripcion, autor, fechaMensaje});
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaMensajes;
    }

}
