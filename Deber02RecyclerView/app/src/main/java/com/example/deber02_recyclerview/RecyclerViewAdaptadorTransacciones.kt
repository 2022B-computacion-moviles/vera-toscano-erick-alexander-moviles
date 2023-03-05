package com.example.deber02_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdaptadorTransacciones (
    private val contexto: RecyclerViewTransacciones,
    private val listaTransacciones: List<transactions>,
    private val recyclerView: RecyclerView
    ): RecyclerView.Adapter<RecyclerViewAdaptadorTransacciones.MyViewHolderTransactions>(){
        inner class MyViewHolderTransactions(view: View) : RecyclerView.ViewHolder(view) {
            val descriptionTextView: TextView
            val valorTextView: TextView
            val nValorTextView: TextView
            init {
                descriptionTextView = view.findViewById(R.id.tv_description)
                valorTextView = view.findViewById(R.id.tv_valor)
                nValorTextView = view.findViewById(R.id.tv_Nvalor)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderTransactions {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.vista_transacciones,
                parent,
                false
            )
        return MyViewHolderTransactions(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolderTransactions, position: Int) {
        val transaccionActual = this.listaTransacciones[position]
        holder.descriptionTextView.text = transaccionActual.descripcion
        holder.valorTextView.text = transaccionActual.valor.toString()
        holder.nValorTextView.text = transaccionActual.nValor.toString()
    }

    override fun getItemCount(): Int {
        return this.listaTransacciones.size
    }

}