package com.example.examen2dobim

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
        val id = intent.getStringExtra("id").toString()

        val btnGuardar = findViewById<Button>(R.id.btnGuardarEstudianteL)

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
                    crudFirebase().crearEstudiante(
                        Estudiantes(
                            "",
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
                    crudFirebase().actualizarEstudiante(
                        Estudiantes(
                            idEstudiante.text.toString(),
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
                crudFirebase().leerEstudiante(id){estudiante->
                    idEstudiante.setText(estudiante.id)
                    nombresEstudiante.setText(estudiante.nombres)
                    apellidosEstudiante.setText(estudiante.apellidos)
                    direccionEstudiante.setText(estudiante.direccion)
                    quintilEstudiante.setText(estudiante.quintil.toString())

                }
            }
            "ver" -> {
                title = "Ver estudiante"
                for (component in components) {
                    component.isEnabled = false
                }
                btnGuardar.visibility = Button.GONE
                crudFirebase().leerEstudiante(id){estudiante->
                    idEstudiante.setText(estudiante.id)
                    nombresEstudiante.setText(estudiante.nombres)
                    apellidosEstudiante.setText(estudiante.apellidos)
                    direccionEstudiante.setText(estudiante.direccion)
                    quintilEstudiante.setText(estudiante.quintil.toString())
                }
            }
        }
    }
}