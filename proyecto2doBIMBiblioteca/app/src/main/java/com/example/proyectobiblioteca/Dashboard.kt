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
    val prestamosDB = db.collection("Prestamos")
    var adaptador: ArrayAdapter<Dashboard>? = null


    var resultAddPrestamo = registerForActivityResult(
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

        updatePrestamoList()

        //boton para crear un prestamo
        val btn_agregarPrestamoLibro = findViewById<Button>(R.id.btn_regPrestamo)
        btn_agregarPrestamoLibro.setOnClickListener {
            val openRegistrarPrestamo = Intent(this, crearPrestamo::class.java)
            openRegistrarPrestamo.putExtra("Usuario", administrador)
            startActivity(openRegistrarPrestamo)
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

        //boton para crear usuario
        val btn_regisUsuario = findViewById<Button>(R.id.btn_regUser)
        btn_regisUsuario.setOnClickListener {
            val openRegistrarAutor = Intent(this, RegistroUsuarios::class.java)
            openRegistrarAutor.putExtra("Usuario", administrador)
            startActivity(openRegistrarAutor)
        }


    }

    fun updatePrestamoList(){

        var layoutPrestamo = findViewById<ViewGroup>(R.id.layout_prestamos)
        val inflater = LayoutInflater.from(this)
            .inflate(R.layout.prestamo_view, layoutPrestamo, false)
        layoutPrestamo.addView(inflater)

        val recyclerViewPrestamos = inflater.findViewById<RecyclerView>(R.id.RView_Prestamos)

        val listaPrestamos = arrayListOf<Prestamo>()
        //función para cargar todos los datos de una colección de firebase en un arraylist



        prestamosDB
            .get()
            .addOnSuccessListener { documents ->
            for (document in documents) {
                listaPrestamos.add(
                    Prestamo(
                        document.id.toString(),
                        "${document.get("nombreLibroPrestamo")}",
                        "${document.get("nombreUsuarioPrestamo")}",
                        "${document.get("fechaMaximaDevolucion")}",
                        "${document.get("estadoDevolucion")}")
                )
            }
                iniciarRecyclerView(
                    listaPrestamos,
                    this,
                    recyclerViewPrestamos
                )
        }.addOnFailureListener { exception ->
            Log.w("Error:", "Error getting documents: ", exception)
        }
    }

    fun iniciarRecyclerView(
        lista: List<Prestamo>,
        actividad: Dashboard,
        recyclerView: RecyclerView
    ){
        val adaptador = AdaptadorPrestamo(
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
        val openlogin = Intent(this, MainActivity::class.java)
        startActivity(openlogin)
    }
}