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
}
