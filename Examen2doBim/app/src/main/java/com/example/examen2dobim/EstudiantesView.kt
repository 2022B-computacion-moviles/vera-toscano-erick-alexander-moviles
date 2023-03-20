package com.example.examen2dobim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class EstudiantesView : AppCompatActivity() {
    var estudiantes = ArrayList<Estudiantes>()
    var todosEstudiantes = ArrayList<Estudiantes>()
    var idSelecionado = ""
    var modo = "crud"
    val contenidoIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val resultado = data.getStringExtra("resultado")
                    if (resultado == "OK") {
                        cargarEstudiantes()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes_view)

        modo = intent.getStringExtra("modo").toString()
        var id = intent.getStringExtra("id").toString()

        var btnCrearEstudiante = findViewById<Button>(R.id.btn_crear_estudiante)
        val spinner = findViewById<Spinner>(R.id.spinnerEstudiantes)

        when (modo) {
            "crud" -> {
                title = "Estudiantes"
                btnCrearEstudiante.setOnClickListener {
                    abrirActividadParametros(AdministrarEstudiantes::class.java, "crear", "")
                }
                spinner.visibility = View.GONE
                cargarEstudiantes()
            }
            "ver" -> {
                btnCrearEstudiante.text = "Inscribir"

                crudFirebase().leerEstudiantes { estudiantes ->
                    todosEstudiantes = estudiantes
                    val adapter = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item,
                        estudiantes.map { it.nombres + " " + it.apellidos })
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }

                btnCrearEstudiante.setOnClickListener {
                    val idEstudiante = todosEstudiantes[spinner.selectedItemPosition].id
                    crudFirebase().existInsripcion(id, idEstudiante) {
                        if (!it) {
                            crudFirebase().crearInscripcion(id, idEstudiante)
                        } else {
                            Toast.makeText(
                                this,
                                "No existe el estudiante o ya esta inscrito en la facultad",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        cargarEstudiantesFacultad(id)
                    }
                }
                cargarEstudiantesFacultad(id)
            }
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_estudiantes, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSelecionado = estudiantes[info.position].id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_estudiante_editar -> {
                abrirActividadParametros(
                    AdministrarEstudiantes::class.java, "editar", idSelecionado
                )
                if (modo == "crud") {
                    cargarEstudiantes()
                } else {
                    cargarEstudiantesFacultad(idSelecionado)
                }
                true
            }
            R.id.menu_estudiante_eliminar -> {
                crudFirebase().eliminarEstudiante(idSelecionado)
                if (modo == "crud") {
                    cargarEstudiantes()
                } else {
                    cargarEstudiantesFacultad(idSelecionado)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun cargarEstudiantesFacultad(id: String) {
        val listView = findViewById<ListView>(R.id.lv_estudiantes)
        crudFirebase().leerInscripcion(id) { estudiantesIDs ->
            crudFirebase().leerEstudiantes { estudiantes ->
                val estudiantesInscritos = estudiantes.filter { estudiantesIDs.contains(it.id) }
                this.estudiantes = estudiantesInscritos as ArrayList<Estudiantes>
                val adaptador = ArrayAdapter(
                    this, android.R.layout.simple_list_item_1, estudiantesInscritos
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
                listView.setOnItemClickListener { _, _, position, _ ->
                    abrirActividadParametros(
                        AdministrarEstudiantes::class.java, "ver", estudiantes[position].id
                    )
                }
            }
        }
    }

    fun cargarEstudiantes() {
        val listView = findViewById<ListView>(R.id.lv_estudiantes)
        crudFirebase().leerEstudiantes { estudiantes ->
            this.estudiantes = estudiantes
            val adaptador = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, estudiantes
            )
            listView.adapter = adaptador
            adaptador.notifyDataSetChanged()
            registerForContextMenu(listView)
            listView.setOnItemClickListener { _, _, position, _ ->
                abrirActividadParametros(
                    AdministrarEstudiantes::class.java, "ver", estudiantes[position].id
                )
            }
        }
    }

    fun abrirActividadParametros(clase: Class<*>, modo: String, id: String) {
        val intentExplicito = Intent(
            this, clase
        )
        intentExplicito.putExtra("modo", modo)
        intentExplicito.putExtra("id", id)
        contenidoIntent.launch(intentExplicito)
    }
}