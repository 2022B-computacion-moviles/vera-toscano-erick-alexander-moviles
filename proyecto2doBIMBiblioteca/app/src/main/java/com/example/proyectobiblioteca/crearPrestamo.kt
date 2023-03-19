package com.example.proyectobiblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class crearPrestamo : AppCompatActivity() {

    var administrador = administrador("","","","")
    var user = Usuario("","","","","","")
    var book = Libro("","","","")
    val db = Firebase.firestore
    val librosDB = db.collection("Libros")
    val usuariosDB = db.collection("Usuarios")
    val prestamosDB = db.collection("Prestamos")
    var idLibroSeleccionado = 0
    var idUsuarioSeleccionado = 0
    val nombre_Libro = arrayListOf<String>()
    val nombre_Usuario = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_prestamo)
    }

    override fun onStart() {
        super.onStart()
        administrador = intent.getParcelableExtra<administrador>("Usuario")!!
        val listLibros = findViewById<Spinner>(R.id.spinnerLibro)
        val listUsuarios = findViewById<Spinner>(R.id.spinnerUsuario)
        val btnCrearPrestamo = findViewById<Button>(R.id.btn_guardarPrestamo)
        val fechaDevolucion = findViewById<EditText>(R.id.tv_fechaDevolucion)
        val estadoDevolucion = findViewById<Switch>(R.id.sw_devuelto)
        var estadoText = ""

        estadoDevolucion.setOnCheckedChangeListener { _, isChecked ->
            estadoText = if (isChecked) estadoDevolucion.textOn as String else estadoDevolucion.textOff as String

        }

        librosDB.get().addOnSuccessListener { result ->
            for (document in result){
                nombre_Libro.add(document.get("nombreLibro").toString())
                Log.i("NombreLibro","${nombre_Libro}")
            }
            val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombre_Libro)
            listLibros.adapter = adaptador

            listLibros.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicionLibro: Int, p3: Long) {
                    idLibroSeleccionado = posicionLibro
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        usuariosDB.get().addOnSuccessListener { result ->
            for (document in result){
                nombre_Usuario.add(document.get("nombreUser").toString())
                Log.i("NombreUsuario","${nombre_Usuario}")
            }
            val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombre_Usuario)
            listUsuarios.adapter = adaptador

            listUsuarios.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicionUsuario: Int, p3: Long) {
                    idUsuarioSeleccionado = posicionUsuario
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }


        btnCrearPrestamo.setOnClickListener {
            var ejercicioAgregado = hashMapOf(
                "idAdministradorPrestamo" to administrador.idUsuario.toString(),
                "nombreLibroPrestamo" to nombre_Libro.elementAt(idLibroSeleccionado).toString(),
                "nombreUsuarioPrestamo" to nombre_Usuario.elementAt(idUsuarioSeleccionado).toString(),
                "fechaMaximaDevolucion" to fechaDevolucion.text.toString(),
                "estadoDevolucion" to estadoText
            )
            prestamosDB.add(ejercicioAgregado).addOnSuccessListener {

                fechaDevolucion.text.clear()
                Toast.makeText(this,"Se registro el ejercicio", Toast.LENGTH_SHORT).show()
                val openInicioUser = Intent(this, Dashboard::class.java)
                openInicioUser.putExtra("Usuario", administrador)
                startActivity(openInicioUser)
            }.addOnFailureListener{
                Log.i("NOOOOO","Failed")
            }
        }

    }
}