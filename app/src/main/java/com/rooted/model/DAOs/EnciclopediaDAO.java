package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rooted.controller.EnciclopediaController;
import com.rooted.database.DatabaseHelper;
import com.rooted.model.entities.Enciclopedia;
import com.rooted.model.entities.EntradasEnciclopedia;

import java.util.ArrayList;

public class EnciclopediaDAO {
    private DatabaseHelper databaseHelper;
    Enciclopedia enciclopedia = Enciclopedia.getInstance();

    public EnciclopediaDAO(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    public void insertarDatosEnciclopedia() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long count = android.database.DatabaseUtils.queryNumEntries(db, "enciclopedia");
        //Log.d("EnciclopediaDAO", "Número de entradas en la tabla enciclopedia antes de insertar: " + count);

        if (count > 0) {
            Log.d("EnciclopediaDAO", "Los datos ya existen, no se insertarán de nuevo.");
            db.close();
            return;
        }

        Enciclopedia enciclopedia = Enciclopedia.getInstance();
        //Log.d("EnciclopediaDAO", "Intentando insertar " + enciclopedia.getEntradasEnciclopedia().size() + " entradas.");

        for (EntradasEnciclopedia entrada : enciclopedia.getEntradasEnciclopedia()) {
            ContentValues values = new ContentValues();
            values.put("nombre", entrada.getNombre());
            values.put("descripcion", entrada.toStringDescripcion());
            values.put("riego", entrada.toStringRiego());
            values.put("forma_plantar", entrada.toStringFormaPlantar());
            values.put("forma_recoger", entrada.toStringFormaRecoger());

            long resultado = db.insert("enciclopedia", null, values);
//            if (resultado == -1) {
//                Log.e("EnciclopediaDAO", "Error al insertar " + entrada.getNombre());
//            } else {
//                Log.d("EnciclopediaDAO", "Insertada entrada: " + entrada.getNombre());
//            }
        }

        db.close();
    }

    public EntradasEnciclopedia buscarPlanta(String nombrePlanta) {
        EntradasEnciclopedia entradasEnciclopedia = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Consulta SQL corregida con marcador de posición
        Cursor cursor = db.rawQuery(
                "SELECT nombre, descripcion, riego, forma_plantar, forma_recoger FROM enciclopedia WHERE nombre = ?",
                new String[]{nombrePlanta} // Pasar nombrePlanta como parámetro
        );

        if (cursor.moveToFirst()) { // Solo necesitamos la primera fila
            String nombre = cursor.getString(0);
            String descripcion = cursor.getString(1);
            String riego = cursor.getString(2);
            String forma_plantar = cursor.getString(3);
            String forma_recoger = cursor.getString(4);

            entradasEnciclopedia = new EntradasEnciclopedia(nombre, descripcion, riego, forma_plantar, forma_recoger);
        }

        // Cerrar cursor y base de datos
        cursor.close();
        db.close();

        return entradasEnciclopedia;
    }


    public boolean addEntradaEnciclopedia(EntradasEnciclopedia entrada) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Enciclopedia enciclopedia = Enciclopedia.getInstance();
        enciclopedia.addEntrada(entrada);

        ContentValues values = new ContentValues();
        values.put("nombre", entrada.getNombre());
        values.put("descripcion", entrada.toStringDescripcion());
        values.put("riego", entrada.toStringRiego());
        values.put("forma_plantar", entrada.toStringFormaPlantar());
        values.put("forma_recoger", entrada.toStringFormaRecoger());

        long resultado = db.insert("enciclopedia", null, values);
        if (resultado == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> getAllPlantasEnciclopedia() {
        ArrayList<String> listaPlantas = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Consulta SQL para obtener solo los nombres de las plantas
        String query = "SELECT " + "nombre" + " FROM " + "enciclopedia";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nombrePlanta = cursor.getString(0); // Obtener el nombre de la planta
                listaPlantas.add(nombrePlanta);
            }
            cursor.close(); // Cerrar el cursor después de usarlo
        }

        db.close(); // Cerrar la base de datos
        return listaPlantas;
    }

    public boolean eliminarPlanta(String nombrePlanta) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int rowsAffected = db.delete("enciclopedia", "nombre = ?", new String[]{nombrePlanta});
        db.close();
        return rowsAffected > 0;
    }



}
