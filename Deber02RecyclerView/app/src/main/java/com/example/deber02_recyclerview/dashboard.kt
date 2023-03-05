package com.example.deber02_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class dashboard : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<cuentas>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val botonTrasactions =findViewById<Button>(R.id.transactions_box)
        botonTrasactions.setOnClickListener{
            irActividad(RecyclerViewTransacciones::class.java)
        }
        val listaCuentas = arrayListOf<cuentas>()
        listaCuentas
            .add(cuentas(1, "Erick", 2204251993,5.0,"Cuenta de Ahorros"))
        listaCuentas
            .add(cuentas(2, "Alexander", 2204251994,150.0,"Cuenta Corriente"))
        val recyclerView = findViewById<RecyclerView>(R.id.rv_cuentas)
        inicializarRecyclerView(listaCuentas, recyclerView)

    }
    fun irActividad(clase: Class<*>){
        val intent= Intent(this, clase)
        startActivity(intent)
    }
    fun inicializarRecyclerView(
        lista: ArrayList<cuentas>,
        recyclerView: RecyclerView
    ) {
        val adaptador = RecyclerViewAdaptadorCuentas(
            this,
            lista,
            recyclerView
        )
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()

    }
}