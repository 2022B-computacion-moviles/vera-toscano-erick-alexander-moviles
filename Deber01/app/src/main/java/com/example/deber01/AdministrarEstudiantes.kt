package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AdministrarEstudiantes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrar_estudiantes)

        val modo = intent.getStringExtra("modo")
        val id = intent.getIntExtra("id", -1)

        val btnGuardar = findViewById<Button>(R.id.btn_materia_guardar)

        val idEstudiante = findViewById<EditText>(R.id.id_estudiante)
        val nombresEstudiante = findViewById<EditText>(R.id.nombre_estudiante)
        val apellidosEstudiante = findViewById<EditText>(R.id.apellido_estudiante)
        val direccionEstudiante = findViewById<EditText>(R.id.direccion_estudiante)
        val quintilEstudiante = findViewById<EditText>(R.id.quintil_estudiante)


        val components = listOf(
            nombresEstudiante,
            apellidosEstudiante,
            direccionEstudiante,
            quintilEstudiante,
        )

        val intentDevolverParametros = Intent()
        when (modo) {
            "crear" -> {
                title = "Crear estudiante"
                for (component in components) {
                    component.isEnabled = true
                }
                btnGuardar.visibility = Button.VISIBLE
                btnGuardar.setOnClickListener {
                    CRUDsqLite(this).crearEstudiante(
                        Estudiantes(
                            0,
                            nombresEstudiante.text.toString(),
                            apellidosEstudiante.text.toString(),
                            direccionEstudiante.text.toString(),
                            quintilEstudiante.text.toString().toInt(),
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
            }
            "editar" -> {
                title = "Editar estudiante"
                for (component in components) {
                    component.isEnabled = true
                }
                btnGuardar.visibility = Button.VISIBLE
                btnGuardar.setOnClickListener {
                    CRUDsqLite(this).actualizarEstudiante(
                        Estudiantes(
                            id,
                            nombresEstudiante.text.toString(),
                            apellidosEstudiante.text.toString(),
                            direccionEstudiante.text.toString(),
                            quintilEstudiante.text.toString().toInt(),
                        )
                    )
                    intentDevolverParametros.putExtra("resultado", "OK")
                    setResult(RESULT_OK, intentDevolverParametros)
                    finish()
                }
                val materia = CRUDsqLite(this).leerEstudiante(id)
                idEstudiante.setText(materia.id.toString())
                nombresEstudiante.setText(materia.nombres)
                apellidosEstudiante.setText(materia.apellidos)
                direccionEstudiante.setText(materia.direccion)
                quintilEstudiante.setText(materia.quintil.toString())
            }
            "ver" -> {
                title = "Ver estudiante"
                for (component in components) {
                    component.isEnabled = false
                }
                btnGuardar.visibility = Button.GONE
                val materia = CRUDsqLite(this).leerEstudiante(id)
                idEstudiante.setText(materia.id.toString())
                nombresEstudiante.setText(materia.nombres)
                apellidosEstudiante.setText(materia.apellidos)
                direccionEstudiante.setText(materia.direccion)
                quintilEstudiante.setText(materia.quintil.toString())
            }
        }
    }
}