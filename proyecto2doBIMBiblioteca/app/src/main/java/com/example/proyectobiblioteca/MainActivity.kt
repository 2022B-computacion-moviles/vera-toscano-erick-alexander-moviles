package com.example.proyectobiblioteca

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore
    val usuarios = db.collection("Usuarios")
    var adaptador: ArrayAdapter<administrador>? = null
    var administrador = administrador("","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val btn_Ingresar = findViewById<Button>(R.id.btn_login)
        btn_Ingresar.setOnClickListener {
            administrador.email = findViewById<EditText>(R.id.input_correoUsuario).text.toString()
            administrador.password = findViewById<EditText>(R.id.input_passwordUsuario).text.toString()

            val passwordEncriptada = Base64.encodeToString(administrador.password.toByteArray(), Base64.DEFAULT)

            usuarios.whereEqualTo("email_usuario", "${administrador.email}")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        usuarios.whereEqualTo("email_usuario", "${administrador.email}")
                            .whereEqualTo("password_usuario", "${passwordEncriptada}")
                            .get()
                            .addOnSuccessListener { querySnapshot2 ->
                                if (!querySnapshot2.isEmpty) {
                                    // Ambas condiciones se cumplen, iniciar sesión
                                    val docSnapshot = querySnapshot2.documents[0] // asumimos que solo hay un documento que cumple la consulta
                                    administrador.nombre = docSnapshot.getString("nombre_usuario").toString()
                                    administrador.idUsuario = docSnapshot.id
                                    findViewById<EditText>(R.id.input_correoUsuario).text.clear()
                                    findViewById<EditText>(R.id.input_passwordUsuario).text.clear()
                                    val openUsuarioInicio = Intent(this, Dashboard::class.java)
                                    openUsuarioInicio.putExtra("Usuario", administrador)
                                    startActivity(openUsuarioInicio)
                                    Log.i("BB","${administrador.idUsuario}")
                                } else {
                                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Usuario no existe", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        val btn_registrar = findViewById<TextView>(R.id.btn_registrar)
        btn_registrar.setOnClickListener {
            irActividad(RegistroAdministrador::class.java)
        }


    }


    fun irActividad(clase: Class<*>){
        val intent=Intent(this, clase)
        startActivity(intent)
    }


}