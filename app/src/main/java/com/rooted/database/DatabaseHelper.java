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
    private static final int DATABASE_VERSION = 15; // Incrementar versión para los nuevos cambios
    private static DatabaseHelper instance;

    // Tabla de usuarios
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_SALT = "salt";

    // Tabla para saber la ultima sesion
    private static final String TABLE_LAST_LOGIN = "last_login";
    private static final String COLUMN_LAST_LOGIN_USERNAME = "username";

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

    public static final String TABLE_PLANTAS_DATA = "datosPlantas";
    public static final String COLUMN_PLANTA_NOMBRE_DATA = "nombrePlantaData";
    public static final String COLUMN_TIEMPO_RIEGO_DATA = "tiempoRiegoPlantaData";
    public static final String COLUMN_TIEMPO_CRECIMIENTO_DATA = "tiempoCrecimientoPlantaData";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Uso de singleton para manejar la base de datos
    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_SALT + " TEXT NOT NULL)";
        db.execSQL(createUsersTable);

        // Crear tabla para ultimo inicio de sesion
        String createLastLoginTable = "CREATE TABLE " + TABLE_LAST_LOGIN + " (" +
                COLUMN_LAST_LOGIN_USERNAME + " TEXT PRIMARY KEY)";
        db.execSQL(createLastLoginTable);


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
        // Llamar al método para insertar datos predeterminados
        insertDefaultPlantData(db);
    }

    private void insertDefaultPlantData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Tomate', " + (7 * 24 * 60 * 60) + ", " + (90 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Lechuga', " + (3 * 24 * 60 * 60) + ", " + (60 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Garbanzos', " + (5 * 24 * 60 * 60) + ", " + (120 * 24 * 60 * 60 * 1000) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Pimientos', " + (6 * 24 * 60 * 60) + ", " + (80 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Cebolla', " + (4 * 24 * 60 * 60) + ", " + (150 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Lentejas', " + (5 * 24 * 60 * 60) + ", " + (90 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Frijoles', " + (4 * 24 * 60 * 60) + ", " + (70 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Habas', " + (6 * 24 * 60 * 60) + ", " + (85 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Guisantes', " + (3 * 24 * 60 * 60) + ", " + (60 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Patata', " + (7 * 24 * 60 * 60) + ", " + (120 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Yuca', " + (10 * 24 * 60 * 60) + ", " + (240 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Pepino', " + (3 * 24 * 60 * 60) + ", " + (50 * 24 * 60 * 60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Calabaza', " + (5 * 24 * 60 * 60) + ", " + (100 * 24 * 60 * 60) + ")");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas existentes si hay una actualización
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAST_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HUERTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENCICLOPEDIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTAS_DATA);

        onCreate(db);
    }
}
