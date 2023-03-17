package com.example.deber01

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
        val id = intent.getIntExtra("id", -1)

        val btnGuardar = findViewById<Button>(R.id.btn_guardar_facultad)
        val etEstudianteId = findViewById<EditText>(R.id.facultad_id)
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
                    CRUDsqLite(this).crearFacultad(
                        Facultades(
                            0,
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
                    CRUDsqLite(this).actualizarFacultad(
                        Facultades(
                            id,
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

    fun cargarFacultad(id: Int) {
        val facultad = CRUDsqLite(this).leerFacultad(id)

        val facultadID = findViewById<EditText>(R.id.facultad_id)
        val facultadNombre = findViewById<EditText>(R.id.nombre_facultad)
        val facultadSiglas = findViewById<EditText>(R.id.siglas_facultad)
        val numEdificio = findViewById<EditText>(R.id.num_edificio)

        facultadID.setText(facultad.id.toString())
        facultadNombre.setText(facultad.nombreFacultad)
        facultadSiglas.setText(facultad.siglas)
        numEdificio.setText(facultad.numEdificio.toString())
    }
}