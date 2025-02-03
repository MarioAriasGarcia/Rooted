package com.rooted.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rooted.model.entities.EncriptacionPassword;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooted.db";
    private static final int DATABASE_VERSION = 26; // Incrementar versión para los nuevos cambios
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
    private static final String COLUMN_MESSAGE_USER_ID = "user_id"; // Clave foránea

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
    public static final String TABLE_ENCICLOPEDIA = "enciclopedia";
    public static final String COLUMN_PLANTA_NOMBRE_E = "nombre";
    public static final String COLUMN_PLANTA_DESC = "descripcion";
    public static final String COLUMN_RIEGO = "riego";
    public static final String COLUMN_FORMA_PLANTAR = "forma_plantar";
    public static final String COLUMN_FORMA_RECOGER = "forma_recoger";

    //Tabla de plantas-data, con los datos por cada planta

    public static final String TABLE_PLANTAS_DATA = "datosPlantas";
    public static final String COLUMN_PLANTA_NOMBRE_DATA = "nombrePlantaData";
    public static final String COLUMN_TIEMPO_RIEGO_DATA = "tiempoRiegoPlantaData";
    public static final String COLUMN_TIEMPO_CRECIMIENTO_DATA = "tiempoCrecimientoPlantaData";

    // Tabla de imágenes de plantas
    private static final String TABLE_PLANTAS_IMAGENES = "plantas_imagenes";
    private static final String COLUMN_IMAGEN_ID = "id";
    private static final String COLUMN_IMAGEN_PLANTA_ID = "planta_id"; // Clave foránea
    private static final String COLUMN_IMAGEN_URI = "uri";





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
        // Crear tabla de usuarios con columna is_admin
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT UNIQUE NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_SALT + " TEXT NOT NULL, " +
                "is_admin INTEGER DEFAULT 0)"; // 0 para usuarios normales, 1 para admin
        db.execSQL(createUsersTable);
        createAdminUser(db);


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
                FECHA + " INTEGER NOT NULL, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";
        db.execSQL(createMessagesTable);

        // Crear tabla de huertos
        String createHuertosTable = "CREATE TABLE " + TABLE_HUERTOS + " (" +
                COLUMN_HUERTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HUERTO_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_HUERTO_SIZE + " INTEGER NOT NULL, " +
                "user_id INTEGER, " + // Relación con el usuario
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";
        db.execSQL(createHuertosTable);


        // Crear tabla de plantas con ON DELETE CASCADE
        String createPlantasTable = "CREATE TABLE " + TABLE_PLANTAS + " (" +
                COLUMN_PLANTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLANTA_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_PLANTA_HUERTO_ID + " INTEGER, " +
                COLUMN_TIEMPO_RIEGO + " INTEGER, " +
                COLUMN_TIEMPO_CRECIMIENTO + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_PLANTA_HUERTO_ID + ") REFERENCES " + TABLE_HUERTOS + "(" + COLUMN_HUERTO_ID + ") ON DELETE CASCADE)";
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

        // Crear tabla de imágenes de plantas
        String createPlantasImagenesTable = "CREATE TABLE " + TABLE_PLANTAS_IMAGENES + " (" +
                COLUMN_IMAGEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGEN_PLANTA_ID + " INTEGER, " +
                COLUMN_IMAGEN_URI + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_IMAGEN_PLANTA_ID + ") REFERENCES " + TABLE_PLANTAS + "(" + COLUMN_PLANTA_ID + ") ON DELETE CASCADE)";
        db.execSQL(createPlantasImagenesTable);




    }

    private void insertDefaultPlantData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Tomate', " + (7) + ", " + (90) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Lechuga', " + (3) + ", " + (60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Garbanzos', " + (5) + ", " + (120) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Pimientos', " + (6) + ", " + (80) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Cebolla', " + (4) + ", " + (150) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Lentejas', " + (5) + ", " + (90) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Frijoles', " + (4) + ", " + (70) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Habas', " + (6) + ", " + (85) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Guisantes', " + (3) + ", " + (60) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Patata', " + (7) + ", " + (120) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Yuca', " + (10) + ", " + (240) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Pepino', " + (3) + ", " + (50) + ")");

        db.execSQL("INSERT INTO " + TABLE_PLANTAS_DATA + " (" +
                COLUMN_PLANTA_NOMBRE_DATA + ", " +
                COLUMN_TIEMPO_RIEGO_DATA + ", " +
                COLUMN_TIEMPO_CRECIMIENTO_DATA + ") VALUES ('Calabaza', " + (5) + ", " + (100) + ")");
    }


    private void createAdminUser(SQLiteDatabase db) {
        // Generar salt y hash para el usuario administrador
        String adminPassword = "admin"; // Cambia esta contraseña según lo necesario
        String salt = EncriptacionPassword.generateSalt(); // Genera un salt único
        String hashedPassword = EncriptacionPassword.encryptPassword(adminPassword, salt); // Hashea la contraseña con el salt

        // Inserta el usuario administrador en la base de datos
        String insertAdminUser = "INSERT INTO " + TABLE_USERS + " (" +
                COLUMN_USERNAME + ", " +
                COLUMN_PASSWORD + ", " +
                COLUMN_SALT + ", " +
                "is_admin) VALUES ('admin', '" + hashedPassword + "', '" + salt + "', 1)";
        db.execSQL(insertAdminUser);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANTAS_IMAGENES);

        onCreate(db);
    }
}
