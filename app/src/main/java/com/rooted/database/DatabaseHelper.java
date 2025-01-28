package com.rooted.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooted.db";
    private static final int DATABASE_VERSION = 9; // Incrementar versión para los nuevos cambios

    // Tabla de usuarios
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Tabla de mensajes
    private static final String TABLE_MESSAGES = "messages";
    private static final String COLUMN_MESSAGE_ID = "id";
    private static final String COLUMN_MESSAGE_TYPE = "message_type";
    private static final String COLUMN_MESSAGE_SUBTYPE = "message_subtype";
    private static final String COLUMN_MESSAGE_CONTENT = "message_content";
    private static final String FECHA = "date";

    // Tabla de huertos
    private static final String TABLE_HUERTOS = "huertos";
    private static final String COLUMN_HUERTO_ID = "id";
    private static final String COLUMN_HUERTO_NOMBRE = "nombre";
    private static final String COLUMN_HUERTO_SIZE = "size";

    // Tabla de plantas-huerto
    private static final String TABLE_PLANTAS = "plantas";
    private static final String COLUMN_PLANTA_ID = "id";
    private static final String COLUMN_PLANTA_NOMBRE = "nombre";
    private static final String COLUMN_TIEMPO_RIEGO = "tiempo_riego";
    private static final String COLUMN_TIEMPO_CRECIMIENTO = "tiempo_crecimiento";
    private static final String COLUMN_PLANTA_HUERTO_ID = "huerto_id"; // Clave foránea

    // Tabla de plantas-enciclopedia
    private static final String TABLE_ENCICLOPEDIA = "enciclopedia";
    private static final String COLUMN_PLANTA_NOMBRE_E = "nombre";
    private static final String COLUMN_PLANTA_DESC = "descripcion";
    private static final String COLUMN_RIEGO = "riego";
    private static final String COLUMN_FORMA_PLANTAR = "forma_plantar";
    private static final String COLUMN_FORMA_RECOGER = "forma_recoger";

    //Tabla de plantas-data, con los datos por cada planta

    private static final String TABLE_PLANTAS_DATA = "datosPlantas";
    private static final String COLUMN_PLANTA_NOMBRE_DATA = "nombrePlantaData";
    private static final String COLUMN_TIEMPO_RIEGO_DATA = "tiempoRiegoPlantaData";
    private static final String COLUMN_TIEMPO_CRECIMIENTO_DATA = "tiempoCrecimientoPlantaData";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL)";
        db.execSQL(createUsersTable);

        // Crear tabla de mensajes
        String createMessagesTable = "CREATE TABLE " + TABLE_MESSAGES + " (" +
                COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MESSAGE_TYPE + " TEXT NOT NULL, " +
                COLUMN_MESSAGE_SUBTYPE + " TEXT, " +
                COLUMN_MESSAGE_CONTENT + " TEXT NOT NULL, "+
                FECHA + " INTEGER NOT NULL)";
        db.execSQL(createMessagesTable);

        // Crear tabla de huertos
        String createHuertosTable = "CREATE TABLE " + TABLE_HUERTOS + " (" +
                COLUMN_HUERTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HUERTO_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_HUERTO_SIZE + " INTEGER NOT NULL, " +
                "user_id INTEGER, " + // Relación con el usuario
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";
        db.execSQL(createHuertosTable);


        // Crear tabla de plantas
        String createPlantasTable = "CREATE TABLE " + TABLE_PLANTAS + " (" +
                COLUMN_PLANTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLANTA_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_PLANTA_HUERTO_ID + " INTEGER, " +
                COLUMN_TIEMPO_RIEGO + " INTEGER, " +
                COLUMN_TIEMPO_CRECIMIENTO + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_PLANTA_HUERTO_ID + ") REFERENCES " + TABLE_HUERTOS + "(" + COLUMN_HUERTO_ID + "))";
        db.execSQL(createPlantasTable);

        // Crear tabla de plantas-enciclopèdia
        String createEnciclopediaTable = "CREATE TABLE " + TABLE_ENCICLOPEDIA + " (" +
                COLUMN_PLANTA_NOMBRE_E + " TEXT PRIMARY KEY, " +
                COLUMN_PLANTA_DESC + " TEXT NOT NULL, " +
                COLUMN_RIEGO + " TEXT NOT NULL, " +
                COLUMN_FORMA_PLANTAR + " TEXT NOT NULL, " +
                COLUMN_FORMA_RECOGER + " TEXT NOT NULL) " ;
        db.execSQL(createEnciclopediaTable);

        // Crear tabla de datos de plantas
        String createPlantasDataTable = "CREATE TABLE " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLANTA_NOMBRE_DATA + " TEXT NOT NULL, " +
                COLUMN_TIEMPO_RIEGO_DATA + " INTEGER, " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + " INTEGER) ";
        db.execSQL(createPlantasDataTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas existentes si hay una actualización
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HUERTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENCICLOPEDIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTAS_DATA);
        onCreate(db);
    }



    // Métodos para gestionar la tabla de plantas
    public long insertPlanta(String nombre, int huertoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLANTA_NOMBRE, nombre);
        values.put(COLUMN_PLANTA_HUERTO_ID, huertoId);

        long result = db.insert(TABLE_PLANTAS, null, values);
        db.close();
        return result; // Devuelve el ID de la planta creada
    }

    public Cursor getPlantasByHuertoId(int huertoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PLANTAS + " WHERE " + COLUMN_PLANTA_HUERTO_ID + " = ?", new String[]{String.valueOf(huertoId)});
    }

    public boolean deletePlanta(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_PLANTAS, COLUMN_PLANTA_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }
}
