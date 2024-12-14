package com.rooted.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.rooted.database.DatabaseHelper;
import com.rooted.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameInput, passwordInput, confirmPasswordInput;
    private Button registerButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.usernameInputRegister);
        registerButton = findViewById(R.id.register_button);
        passwordInput = findViewById(R.id.passwordInputRegister);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        databaseHelper = new DatabaseHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String confirmPassword = confirmPasswordInput.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isRegistered = databaseHelper.registerUser(username, password);
                    if (isRegistered) {
                        Toast.makeText(RegisterActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                        finish(); // Cierra la actividad
                    } else {
                        Toast.makeText(RegisterActivity.this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button loginRedirectButton = findViewById(R.id.login_redirect_button);
        loginRedirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
