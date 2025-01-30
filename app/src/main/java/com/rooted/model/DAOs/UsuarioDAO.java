package com.rooted.model.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.rooted.database.DatabaseHelper;
import com.rooted.model.entities.EncriptacionPassword;

public class UsuarioDAO {
    private DatabaseHelper databaseHelper;

    public UsuarioDAO(Context context) {
        databaseHelper = DatabaseHelper.getInstance(context);
    }

    // Validar credenciales de usuario
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username = ? AND password = ?",
                new String[]{username, password}
        );
        boolean isValid = cursor.moveToFirst(); // Existe al menos un usuario con esas credenciales
        cursor.close();
        createLastLogin(username);
        return isValid;
    }

    // Obtener el ID del usuario por su nombre
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM users WHERE username = ?",
                new String[]{username}
        );

        int userId = -1; // ID no encontrado
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }

    public boolean registerUser(String username, String password) {
        String salt = EncriptacionPassword.generateSalt();
        String hashPassword = EncriptacionPassword.encryptPassword(password, salt);

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("saltTEXT", salt);

        long result = db.insert("users", null, values);
        db.close();
        return result != -1; // Devuelve true si el registro fue exitoso
    }

    public boolean createLastLogin(String username){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        long result = db.insert("last_login", null, values);
        return result != -1; // Devuelve true si el registro fue exitoso
    }

    public String readLastLogin() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String lastUser = null;

        Cursor cursor = db.rawQuery("SELECT username FROM last_login", null);
        if (cursor.moveToFirst()) {
            lastUser = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return lastUser;
    }

    public void deleteLastLogin() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Elimina todos los registros de usuarios almacenados para simular un cierre de sesión
        db.execSQL("DELETE FROM last_login");
        db.close();
    }

    public String getUserSalt(String user){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String salt = "";
        Cursor cursor = db.rawQuery(
                "SELECT saltTEXT FROM users WHERE username = ?",
                new String[]{salt}
        );
        cursor.close();
        return salt;
    }
}
