package com.example.deber02_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdaptadorCuentas (
    private val contexto: dashboard,
    private val listaCuentas: List<cuentas>,
    private val recyclerView: RecyclerView,
    ): RecyclerView.Adapter<RecyclerViewAdaptadorCuentas.MyViewHolderCuentas>() {


        inner class MyViewHolderCuentas(view: View) : RecyclerView.ViewHolder(view) {
            val nombreCuenta: TextView
            val numCuenta: TextView
            val saldo: TextView
            val tCuenta: TextView

            init {
                nombreCuenta = view.findViewById(R.id.tv_cuenta)
                numCuenta = view.findViewById(R.id.tv_nCuenta)
                saldo = view.findViewById(R.id.tv_saldoD)
                tCuenta = view.findViewById(R.id.tv_tipoCuenta)
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderCuentas {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.vista_cuentas,
                parent,
                false
            )
        return MyViewHolderCuentas(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolderCuentas, position: Int) {
        val cuentaActual = this.listaCuentas[position]
        holder.nombreCuenta.text = cuentaActual.nombreCuenta
        holder.numCuenta.text = cuentaActual.numCuenta.toString()
        holder.saldo.text = cuentaActual.saldo.toString()
        holder.tCuenta.text = cuentaActual.tipoCuenta
    }

    override fun getItemCount(): Int {
        return this.listaCuentas.size
    }

}

