package com.example.proyectobiblioteca

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroAdministrador : AppCompatActivity() {

    val db = Firebase.firestore
    val administradores = db.collection("Administradores")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuario)
    }

    override fun onStart() {
        super.onStart()
        val nombreAdministradorF = findViewById<EditText>(R.id.input_nombreRegistro)
        val correoAdministradorF = findViewById<EditText>(R.id.input_correoRegistro)
        val contraseñaAdministradorF = findViewById<EditText>(R.id.input_passwordRegistro)


        val btn_registrar = findViewById<Button>(R.id.btn_crearCuenta)
        btn_registrar.setOnClickListener {
            //se encripta la contraseña
            val contraseñaEncriptada = Base64.encodeToString(contraseñaAdministradorF.text.toString().toByteArray(), Base64.DEFAULT)
            var usuario = hashMapOf(
                "nombreAdministrador" to nombreAdministradorF.text.toString(),
                "emailAdministrador" to correoAdministradorF.text.toString(),
                "contraseñaAdministrador" to contraseñaEncriptada
            )
            if (nombreAdministradorF.text.toString().isEmpty() || correoAdministradorF.text.toString().isEmpty() || contraseñaAdministradorF.text.toString().isEmpty()) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                administradores.whereEqualTo("email_usuario", "${correoAdministradorF.text.toString()}")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                        } else {
                            administradores.add(usuario)
                            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                            nombreAdministradorF.text.clear()
                            correoAdministradorF.text.clear()
                            contraseñaAdministradorF.text.clear()
                            //regresar a login
                            finish()
                        }
                    }
            }


        }
        val btn_login = findViewById<TextView>(R.id.btn_logind)
        btn_login.setOnClickListener {
            finish()
        }
    }


}