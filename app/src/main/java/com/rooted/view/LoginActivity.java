package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.rooted.R;
import com.rooted.database.DatabaseHelper;
import com.rooted.model.Usuario;
import com.rooted.ui.theme.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput;
    private Button loginButton, registerButton;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInputLogin);
        passwordInput = findViewById(R.id.passwordInputLogin);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_redirect_button);
        databaseHelper = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();



                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = databaseHelper.validateUser(username, password);
                    if (isValid) {
                        Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                        // Cargar el usuario desde la base de datos
                        String usernameFromDb = username;
                        int userId = databaseHelper.getUserIdByUsername(username);

                        Usuario usuario = new Usuario(userId, usernameFromDb, password);

                        // Pasar el objeto Usuario al Intent
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user_id", userId);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish(); // Finaliza la LoginActivity
                    } else {
                        Toast.makeText(LoginActivity.this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




    }
}