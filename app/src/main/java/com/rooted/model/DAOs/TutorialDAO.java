package com.rooted.model.DAOs;

import com.rooted.database.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TutorialDAO {
    private DatabaseHelper databaseHelper;
    public TutorialDAO(Context context){databaseHelper = DatabaseHelper.getInstance(context);}

    public String obtenerTutorialesUri(String nombreVideo) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("tutoriales", new String[]{"uri_video"}, "nombre_video = ?",
                new String[]{String.valueOf(nombreVideo)}, null, null, null);

        String uri = null;
        if (cursor.moveToFirst()) {
            uri = cursor.getString(0); // Obtener el valor del salt
        }
        db.close();
        return uri;
    }
    public void addVideo(String nombreVideo, String uri) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uri_video", uri);
        values.put("nombre_video", nombreVideo);
        db.insert("tutoriales", null, values);
    }
}
