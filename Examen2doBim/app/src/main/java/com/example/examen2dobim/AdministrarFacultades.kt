package com.example.examen2dobim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlin.collections.ArrayList

class AdministrarFacultades : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_facultades)

        val modo = intent.getStringExtra("modo")
        val id = intent.getStringExtra("id").toString()

        val btnGuardar = findViewById<Button>(R.id.btn_guardar_facultad)
        val FacultadID = findViewById<EditText>(R.id.facultad_id)
        val nombreFacultad = findViewById<EditText>(R.id.nombre_facultad)
        val siglasFacultad = findViewById<EditText>(R.id.siglas_facultad)
        val numEdificio = findViewById<EditText>(R.id.num_edificio)

        val components = listOf(
            nombreFacultad,
            siglasFacultad,
            numEdificio,
        )

        val intentDevolverParametros = Intent()
        when (modo) {
            "crear" -> {
                title = "Crear facultad"
                for (component in components) {
                    component.isEnabled = true
                }
                btnGuardar.visibility = Button.VISIBLE
                btnGuardar.setOnClickListener {
                    crudFirebase().crearFacultad(
                        Facultades(
                            "",
                            nombreFacultad.text.toString(),
                            siglasFacultad.text.toString(),
                            numEdificio.text.toString().toInt(),
                            ArrayList(),
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
            }
            "editar" -> {
                title = "Editar facultad"
                btnGuardar.visibility = Button.VISIBLE
                cargarFacultad(id)
                btnGuardar.setOnClickListener {
                    crudFirebase().actualizarFacultad(
                        Facultades(
                            FacultadID.text.toString(),
                            nombreFacultad.text.toString(),
                            siglasFacultad.text.toString(),
                            numEdificio.text.toString().toInt(),
                            ArrayList(),
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
            }
            "ver" -> {
                title = "Ver facultad"
                for (component in components) {
                    component.isEnabled = false
                }
                btnGuardar.visibility = Button.INVISIBLE
                cargarFacultad(id)
            }
        }

    }

    fun cargarFacultad(id: String) {

        val facultadID = findViewById<EditText>(R.id.facultad_id)
        val facultadNombre = findViewById<EditText>(R.id.nombre_facultad)
        val facultadSiglas = findViewById<EditText>(R.id.siglas_facultad)
        val numEdificio = findViewById<EditText>(R.id.num_edificio)

        crudFirebase().leerFacultad(id) { facultad ->
            facultadID.setText(facultad.id)
            facultadNombre.setText(facultad.nombreFacultad)
            facultadSiglas.setText(facultad.siglas)
            numEdificio.setText(facultad.numEdificio.toString())
        }

    }
}