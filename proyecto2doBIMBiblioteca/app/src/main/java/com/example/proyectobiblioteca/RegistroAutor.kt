package com.example.proyectobiblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroAutor : AppCompatActivity() {
    var administrador = administrador("", "", "", "")
    val db = Firebase.firestore
    val autoresDB = db.collection("Autores")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_autores)
    }

    override fun onStart() {
        super.onStart()
        administrador = intent.getParcelableExtra<administrador>("Usuario")!!
        val nombresAutorF = findViewById<EditText>(R.id.input_nombresAutor)
        val apellidosAutorF = findViewById<EditText>(R.id.input_apellidosAutor)
        val nacionalidadAutorF = findViewById<EditText>(R.id.input_nacionalidadAutor)

        val btn_guardadAutor = findViewById<Button>(R.id.btn_guardarAutor)
        btn_guardadAutor.setOnClickListener {

            var autor = hashMapOf(
                "nombresAutor" to nombresAutorF.text.toString(),
                "apellidosAutor" to apellidosAutorF.text.toString(),
                "nacionalidadAutor" to nacionalidadAutorF.text.toString(),
            )
            if (nombresAutorF.text.toString().isEmpty() || apellidosAutorF.text.toString()
                    .isEmpty() || nacionalidadAutorF.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                autoresDB.add(autor)
                Toast.makeText(this, "Libro registrado", Toast.LENGTH_SHORT).show()
                nombresAutorF.text.clear()
                apellidosAutorF.text.clear()
                nacionalidadAutorF.text.clear()
                //regresar la anterior actividad
                val openInicioUser = Intent(this, Dashboard::class.java)
                openInicioUser.putExtra("Usuario", administrador)
                startActivity(openInicioUser)
            }


        }
    }
}