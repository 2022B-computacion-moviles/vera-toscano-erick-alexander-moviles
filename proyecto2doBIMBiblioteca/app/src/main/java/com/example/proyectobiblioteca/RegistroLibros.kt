package com.example.proyectobiblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroLibros : AppCompatActivity() {
    var administrador = administrador("", "", "", "")
    val db = Firebase.firestore
    val librosDB = db.collection("Libros")
    val autoresDB = db.collection("Autores")
    var idAutorSeleccionado = 0
    val nombreAutor = arrayListOf<String>()
    val apellidoAutor = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_libros)
    }

    override fun onStart() {
        super.onStart()
        administrador = intent.getParcelableExtra<administrador>("Usuario")!!
        val nombreLibroF = findViewById<EditText>(R.id.input_nombreLibro)
        val fechaLibroF = findViewById<EditText>(R.id.input_fechaLibro)
        val editorialLibroF = findViewById<EditText>(R.id.input_editorial)
        val listAutores = findViewById<Spinner>(R.id.spinnerAutor)

        autoresDB.get().addOnSuccessListener { result ->
            for (document in result){
                nombreAutor.add(document.get("nombresAutor" ).toString()+" "+document.get("apellidosAutor" ).toString())
                Log.i("nombreAutor","${nombreAutor}")
            }



            val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, nombreAutor)
            listAutores.adapter = adaptador

            listAutores.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicionLibro: Int, p3: Long) {
                    idAutorSeleccionado = posicionLibro
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        val btn_guardarLibro = findViewById<Button>(R.id.btn_guardarLibro)
        btn_guardarLibro.setOnClickListener {

            var libro = hashMapOf(
                "nombreLibro" to nombreLibroF.text.toString(),
                "fechaLibro" to fechaLibroF.text.toString(),
                "editorialLibro" to editorialLibroF.text.toString(),
                "autorLibro" to nombreAutor.elementAt(idAutorSeleccionado).toString()
            )
            if (nombreLibroF.text.toString().isEmpty() || fechaLibroF.text.toString()
                    .isEmpty() || editorialLibroF.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                librosDB.add(libro)
                Toast.makeText(this, "Libro registrado", Toast.LENGTH_SHORT).show()
                nombreLibroF.text.clear()
                fechaLibroF.text.clear()
                editorialLibroF.text.clear()

                //regresar la anterior actividad
                val openInicioUser = Intent(this, Dashboard::class.java)
                openInicioUser.putExtra("Usuario", administrador)
                startActivity(openInicioUser)
            }


        }
    }
}