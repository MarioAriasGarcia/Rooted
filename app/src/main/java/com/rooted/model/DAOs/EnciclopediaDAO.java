package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.rooted.database.DatabaseHelper;
import com.rooted.model.entities.Enciclopedia;
import com.rooted.model.entities.EntradasEnciclopedia;

public class EnciclopediaDAO {
    private DatabaseHelper databaseHelper;
    Enciclopedia enciclopedia = new Enciclopedia();

    public EnciclopediaDAO(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

    public void insertarDatosEnciclopedia() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        enciclopedia = enciclopedia.getInstance();

        for (EntradasEnciclopedia entrada : enciclopedia.entradasEnciclopedia) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_PLANTA_NOMBRE_E, entrada.getNombre());
            values.put(DatabaseHelper.COLUMN_PLANTA_DESC, entrada.toStringDescripcion());
            values.put(DatabaseHelper.COLUMN_RIEGO, entrada.toStringRiego());
            values.put(DatabaseHelper.COLUMN_FORMA_PLANTAR, entrada.toStringFormaPlantar());
            values.put(DatabaseHelper.COLUMN_FORMA_RECOGER, entrada.toStringFormaRecoger());

            db.insert(DatabaseHelper.TABLE_ENCICLOPEDIA, null, values);
        }

        db.close();
    }
}
