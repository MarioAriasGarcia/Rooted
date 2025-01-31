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
public class EnciclopediaDAO {
    private DatabaseHelper databaseHelper;

    public EnciclopediaDAO(Context context) {
        this.databaseHelper = DatabaseHelper.getInstance(context);
    }

}

