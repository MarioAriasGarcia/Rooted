package com.rooted.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooted.db";
    private static final int DATABASE_VERSION = 5; // Incrementar versión para los nuevos cambios

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

    // Tabla de huertos
    private static final String TABLE_HUERTOS = "huertos";
    private static final String COLUMN_HUERTO_ID = "id";
    private static final String COLUMN_HUERTO_NOMBRE = "nombre";
    private static final String COLUMN_HUERTO_SIZE = "size";

    // Tabla de plantas
    private static final String TABLE_PLANTAS = "plantas";
    private static final String COLUMN_PLANTA_ID = "id";
    private static final String COLUMN_PLANTA_NOMBRE = "nombre";
    private static final String COLUMN_PLANTA_HUERTO_ID = "huerto_id"; // Clave foránea

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
                COLUMN_MESSAGE_CONTENT + " TEXT NOT NULL)";
        db.execSQL(createMessagesTable);

        // Crear tabla de huertos
        String createHuertosTable = "CREATE TABLE " + TABLE_HUERTOS + " (" +
                COLUMN_HUERTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HUERTO_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_HUERTO_SIZE + " INTEGER NOT NULL)";
        db.execSQL(createHuertosTable);

        // Crear tabla de plantas
        String createPlantasTable = "CREATE TABLE " + TABLE_PLANTAS + " (" +
                COLUMN_PLANTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLANTA_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_PLANTA_HUERTO_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_PLANTA_HUERTO_ID + ") REFERENCES " + TABLE_HUERTOS + "(" + COLUMN_HUERTO_ID + "))";
        db.execSQL(createPlantasTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas existentes si hay una actualización
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HUERTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTAS);
        onCreate(db);
    }

    // Registrar un nuevo usuario
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1; // Devuelve true si se registra correctamente
    }

    // Validar usuario durante el inicio de sesión
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

    // Métodos para manejar la tabla de mensajes
    public boolean insertMessage(String messageType, String messageSubtype, String messageContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_TYPE, messageType);
        values.put(COLUMN_MESSAGE_SUBTYPE, messageSubtype);
        values.put(COLUMN_MESSAGE_CONTENT, messageContent);

        long result = db.insert(TABLE_MESSAGES, null, values);
        db.close();
        return result != -1; // Devuelve true si la inserción fue exitosa
    }

    public Cursor getAllMessages() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGES;
        return db.rawQuery(query, null);
    }

    // Métodos para gestionar la tabla de huertos
    public long insertHuerto(String nombre, int size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HUERTO_NOMBRE, nombre);
        values.put(COLUMN_HUERTO_SIZE, size);

        long result = db.insert(TABLE_HUERTOS, null, values);
        db.close();
        return result; // Devuelve el ID del huerto creado
    }

    public Cursor getAllHuertos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HUERTOS, null);
    }

    public Cursor getHuertoById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HUERTOS + " WHERE " + COLUMN_HUERTO_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean updateHuerto(int id, String nombre, int size) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_HUERTO_NOMBRE, nombre);
        values.put(COLUMN_HUERTO_SIZE, size);

        int rows = db.update(TABLE_HUERTOS, values, COLUMN_HUERTO_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public boolean deleteHuerto(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_HUERTOS, COLUMN_HUERTO_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
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
