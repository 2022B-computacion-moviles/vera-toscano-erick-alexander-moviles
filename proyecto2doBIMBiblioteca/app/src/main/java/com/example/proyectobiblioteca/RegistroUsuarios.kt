package com.example.proyectobiblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroUsuarios : AppCompatActivity()  {
    var administrador = administrador("", "", "", "")
    val db = Firebase.firestore
    val usuariosDB = db.collection("Usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_usuarios)
    }

    override fun onStart() {
        super.onStart()
        administrador = intent.getParcelableExtra<administrador>("Usuario")!!
        val nombresUsuarioF = findViewById<EditText>(R.id.input_nombresUser)
        val fechaNacimientoUserF = findViewById<EditText>(R.id.input_fNacimientoUser)
        val direccionUserF = findViewById<EditText>(R.id.input_direccionUser)
        val telefonoUserF = findViewById<EditText>(R.id.input_phoneUser)
        val correoUserF = findViewById<EditText>(R.id.input_emailUser)


        val btn_registrar = findViewById<Button>(R.id.btn_guardarUsuario)
        btn_registrar.setOnClickListener {

            var usuario = hashMapOf(
                "nombreUser" to nombresUsuarioF.text.toString(),
                "nacimientoUser" to fechaNacimientoUserF.text.toString(),
                "direccionUser" to direccionUserF.text.toString(),
                "telefonoUser" to telefonoUserF.text.toString(),
                "email_usuario" to correoUserF.text.toString(),
            )
            if (nombresUsuarioF.text.toString().isEmpty()
                || fechaNacimientoUserF.text.toString().isEmpty()
                || direccionUserF.text.toString().isEmpty()
                || telefonoUserF.text.toString().isEmpty()
                || correoUserF.text.toString().isEmpty()) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                usuariosDB.add(usuario)
                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                nombresUsuarioF.text.clear()
                fechaNacimientoUserF.text.clear()
                direccionUserF.text.clear()
                telefonoUserF.text.clear()
                correoUserF.text.clear()

                //regresar la anterior actividad
                val openInicioUser = Intent(this, Dashboard::class.java)
                openInicioUser.putExtra("Usuario", administrador)
                startActivity(openInicioUser)
            }


        }
    }


}