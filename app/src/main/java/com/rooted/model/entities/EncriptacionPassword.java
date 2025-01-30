package com.rooted.model.entities;

import android.os.Build;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
public class EncriptacionPassword {
    public static String generateSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(salt);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error generando el salt", e);
        }
        return null;
    }

    public static String encryptPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password; // Combinar el salt con la contraseña
            byte[] hashedBytes = md.digest(saltedPassword.getBytes("UTF-8"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(hashedBytes);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error encriptando la contraseña", e);
        }
        return null;
    }
}
