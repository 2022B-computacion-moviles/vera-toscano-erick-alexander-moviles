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
    val db = Firebase.firestore
    val librosDB = db.collection("Libros")
    val prestamosDB = db.collection("Prestamos")
    var idEjercicioSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_prestamo)
    }

    override fun onStart() {
        super.onStart()
        administrador = intent.getParcelableExtra<administrador>("Usuario")!!
        val listLibros = findViewById<Spinner>(R.id.sp_ejercicio)

        val btnCrearPrestamo = findViewById<Button>(R.id.btn_guardarPrestamo)

        val in_series = findViewById<EditText>(R.id.input_numeroSeries)
        val in_repeticiones = findViewById<EditText>(R.id.input_numeroRepeticiones)
        val in_peso = findViewById<EditText>(R.id.input_peso)

        librosDB.get().addOnSuccessListener { result ->
            val nombre_Libro = arrayListOf<String>()

            for (document in result){
                nombre_Libro.add(document.get("nombreLibro").toString())
                Log.i("NombreLibro","${nombre_Libro}")
            }
            val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombre_Libro)
            listLibros.adapter = adaptador

            listLibros.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicionEjercicio: Int, p3: Long) {
                    idEjercicioSeleccionado = posicionEjercicio
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

            btnCrearPrestamo.setOnClickListener {
                var ejercicioAgregado = hashMapOf(
                    "idusuario_ejercicio" to administrador.idUsuario.toString(),
                    "nombre_ejercicio" to nombre_Libro.elementAt(idEjercicioSeleccionado).toString(),
                    "series_ejercicio" to in_series.text.toString(),
                    "repeticiones_ejercicio" to in_repeticiones.text.toString(),
                    "peso_ejercicio" to in_peso.text.toString()
                )
                prestamosDB.add(ejercicioAgregado).addOnSuccessListener {
                    in_series.text.clear()
                    in_peso.text.clear()
                    in_repeticiones.text.clear()
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
}