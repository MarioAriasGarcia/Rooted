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
import com.rooted.view.SoporteActivity
import com.rooted.view.TutorialesActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Recuperar el objeto Usuario desde el Intent
        val usuario = intent.getSerializableExtra("USER_OBJECT") as? Usuario

        if (usuario != null) {
            // Mostrar mensaje de bienvenida o usar los datos del usuario
            val nombreUsuario = usuario.nombreUsuario
            Toast.makeText(this, "Bienvenido, $nombreUsuario", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No se pudo cargar la información del usuario", Toast.LENGTH_SHORT).show()
        }

        // Configurar los botones como antes
        val gestionarHuertosButton = findViewById<Button>(R.id.gestionar_huertos_button)
        gestionarHuertosButton.setOnClickListener {
            val intent = Intent(this@MainActivity, GestionarHuertosActivity::class.java)
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
    }
}
