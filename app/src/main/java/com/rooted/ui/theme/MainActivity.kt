package com.rooted.ui.theme


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rooted.R
import com.rooted.model.Usuario
import com.rooted.view.EnciclopediaActivity
import com.rooted.view.GestionarHuertosActivity
import com.rooted.view.ProfileActivity
import com.rooted.view.SoporteActivity
import com.rooted.view.TutorialesActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recuperar el user_id y el username desde el Intent
        val userId = intent.getIntExtra("user_id", -1)
        val nombreUsuario = intent.getStringExtra("username")

        // Verificar que el user_id sea válido
        if (userId == -1) {
            Toast.makeText(this, "Error: No se pudo cargar el ID del usuario", Toast.LENGTH_SHORT).show()
            finish() // Finalizar actividad si no hay un user_id válido
            return
        }

        // Mostrar un mensaje de bienvenida
        Toast.makeText(this, "Bienvenido, usuario: $nombreUsuario", Toast.LENGTH_SHORT).show()

        // Configurar los botones
        val gestionarHuertosButton = findViewById<Button>(R.id.gestionar_huertos_button)
        gestionarHuertosButton.setOnClickListener {
            val intent = Intent(this@MainActivity, GestionarHuertosActivity::class.java)
            intent.putExtra("user_id", userId) // Pasar el user_id a la siguiente actividad
            intent.putExtra("username", nombreUsuario) // Pasar el nombre de usuario a la siguiente actividad
            startActivity(intent)
        }

        val tutorialesButton = findViewById<Button>(R.id.tutoriales_button)
        tutorialesButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TutorialesActivity::class.java)
            startActivity(intent)
        }

        val enciclopediaButton = findViewById<Button>(R.id.enciclopedia_button)
        enciclopediaButton.setOnClickListener {
            val intent = Intent(this@MainActivity, EnciclopediaActivity::class.java)
            startActivity(intent)
        }

        val soporteButton = findViewById<Button>(R.id.soporte_button)
        soporteButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SoporteActivity::class.java)
            startActivity(intent)
        }

        val miPerfilButton = findViewById<Button>(R.id.miPerfil_button)
        miPerfilButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            intent.putExtra("user_id", userId) // Pasar el user_id a la siguiente actividad
            intent.putExtra("username", nombreUsuario) // Pasar el nombre de usuario a la siguiente actividad
            startActivity(intent)
        }
    }
}
