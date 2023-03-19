package com.example.proyectobiblioteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorPrestamo(
    private val context: Dashboard,
    private val prestamos: List<Prestamo>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AdaptadorPrestamo.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        var libroPrestamo: TextView
        var usuarioPrestamo: TextView
        var fechaDevolucionPrestamo: TextView
        var estadoDevolucion: TextView
        init {
            libroPrestamo = view.findViewById(R.id.nombre_libro)
            usuarioPrestamo = view.findViewById(R.id.nUsuario)
            fechaDevolucionPrestamo = view.findViewById(R.id.repeticiones_ejercicio)
            estadoDevolucion = view.findViewById(R.id.peso_ejercicio)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_prestamo, //Definimos la vista de nuestro recyvler
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val prestamoA = prestamos[position]
        holder.libroPrestamo.text = prestamoA.libro
        holder.usuarioPrestamo.text= prestamoA.usuario.toString()
        holder.fechaDevolucionPrestamo.text = prestamoA.fechaMaximaDevolución.toString()
        holder.estadoDevolucion.text = prestamoA.devuelto.toString()
    }

    override fun getItemCount(): Int {
        return prestamos.size
    }

}