package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rooted.database.DatabaseHelper;

public class PlantaDAO {
    private DatabaseHelper databaseHelper;

    public PlantaDAO(Context context){
        this.databaseHelper = new DatabaseHelper(context);
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

    public int getTiempoRiego(String nombrePlanta){
        return 0;
    }

    public int getTiempoCrecimiento(String nombrePlanta){
        return 0;
    }

}
