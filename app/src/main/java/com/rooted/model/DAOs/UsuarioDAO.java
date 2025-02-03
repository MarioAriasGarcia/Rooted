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
        if(isValid){
            System.out.println("Existe ese nombre de usuario");
        }else{
            System.out.println("No existe ese nombre de usuario");
        }
        cursor.close();
        if(isValid){
            createLastLogin(username);
        }
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

    // Obtener el ID del usuario por su nombre
    public String getUserNameByUserId(int userId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT username FROM users WHERE id = ?",
                new String[]{String.valueOf(userId)}
        );

        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(0); // Obtener el valor de la primera columna
        }

        cursor.close(); // Cerrar el cursor para evitar fugas de memoria
        return userName;
    }

    public boolean registerUser(String username, String password) {
        // Validar si el nombre de usuario ya está tomado
        if (isUsernameTaken(username)) {
            return false; // Devuelve falso si el nombre de usuario ya existe
        }

        // Generar el salt y la contraseña encriptada
        String salt = EncriptacionPassword.generateSalt();
        String hashPassword = EncriptacionPassword.encryptPassword(password, salt);

        // Insertar el nuevo usuario en la base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", hashPassword); // Guardar la contraseña encriptada
        values.put("salt", salt);

        long result = db.insert("users", null, values);
        db.close();
        return result != -1; // Devuelve true si el registro fue exitoso
    }


    public boolean isUsernameTaken(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT username FROM users WHERE username = ?",
                new String[]{username}
        );

        boolean exists = cursor.moveToFirst(); // Si hay al menos un registro
        cursor.close();
        System.out.println("Verificando si el usuario está en uso: " + username);
        System.out.println("Resultado de isUsernameTaken: " + exists);

        return exists;
    }


    public boolean createLastLogin(String username){
        deleteLastLogin();
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

    public String getUserSalt(String user) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT salt FROM users WHERE username = ?",
                new String[]{user} // Cambiado para usar el parámetro "user"
        );

        String salt = null;
        if (cursor.moveToFirst()) {
            salt = cursor.getString(0); // Obtener el valor del salt
        }
        cursor.close();
        return salt;
    }

    public boolean isAdmin(String username) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"is_admin"},
                "username = ?", new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            boolean isAdmin = cursor.getInt(0) == 1;
            cursor.close();
            return isAdmin;
        }
        return false;
    }





}
