package com.example.proyectobiblioteca

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Dashboard : AppCompatActivity() {

    var idItemSeleccionado = 0
    var administrador = administrador("","","","")
    val db = Firebase.firestore
    val ejercicioRegistrados = db.collection("Prestamos")
    var adaptador: ArrayAdapter<Dashboard>? = null


    var resultAddEjercicio = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null) {
                val data = result.data
                administrador = intent.getParcelableExtra<administrador>("Usuario")!!
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        Log.i("Login","${administrador.nombre}")
    }

    override fun onStart() {
        super.onStart()

        administrador = intent.getParcelableExtra<administrador>("Usuario")!!

        val tv_usuario = findViewById<TextView>(R.id.tv_usuario)
        tv_usuario.setText("${administrador.nombre}")

        updateEjerciciosList()

        //boton para crear un prestamo
        val btn_agregarEjercicio = findViewById<Button>(R.id.btn_regPrestamo)
        btn_agregarEjercicio.setOnClickListener {
            val openAgregarEjercicio = Intent(this, crearPrestamo::class.java)
            openAgregarEjercicio.putExtra("Usuario", administrador)
            startActivity(openAgregarEjercicio)
        }

        //boton para crear un libro
        val btn_regisLibro = findViewById<Button>(R.id.btn_regLibro)
        btn_regisLibro.setOnClickListener {
            val openRegistrarLibro = Intent(this, RegistroLibros::class.java)
            openRegistrarLibro.putExtra("Usuario", administrador)
            startActivity(openRegistrarLibro)
        }

        //boton para crear un autor
        val btn_regisAutor = findViewById<Button>(R.id.btn_regAutor)
        btn_regisAutor.setOnClickListener {
            val openRegistrarAutor = Intent(this, RegistroAutor::class.java)
            openRegistrarAutor.putExtra("Usuario", administrador)
            startActivity(openRegistrarAutor)
        }

    }

    fun updateEjerciciosList(){

        var layoutEjercicios = findViewById<ViewGroup>(R.id.layout_ejercicios)
        val inflater = LayoutInflater.from(this)
            .inflate(R.layout.ejercicios_view, layoutEjercicios, false)
        layoutEjercicios.addView(inflater)

        val recyclerViewEjercicios = inflater.findViewById<RecyclerView>(R.id.rcv_ejercicios)

        val listaEjercicios = arrayListOf<Ejercicio>()

        ejercicioRegistrados.whereEqualTo("idusuario_ejercicio","${administrador.idUsuario}")
            .get()
            .addOnSuccessListener { documents ->
            for (document in documents) {
                listaEjercicios.add(
                    Ejercicio(
                        document.id.toString(),
                        "${document.get("nombre_ejercicio")}",
                        "${document.get("series_ejercicio")}",
                        "${document.get("repeticiones_ejercicio")}",
                        "${document.get("peso_ejercicio")} KG")
                )
            }
                iniciarRecyclerView(
                    listaEjercicios,
                    this,
                    recyclerViewEjercicios
                )
        }.addOnFailureListener { exception ->
            Log.w("ñkasdjklasjda", "Error getting documents: ", exception)
        }
    }

    fun iniciarRecyclerView(
        lista: List<Ejercicio>,
        actividad: Dashboard,
        recyclerView: RecyclerView
    ){
        val adaptador = EjercicioListAdapter(
            actividad,
            lista,
            recyclerView
        )
        recyclerView.adapter= adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividad)
        adaptador.notifyDataSetChanged()
    }


    fun cerrarSesion(view: View?) {
        finish()
    }
}