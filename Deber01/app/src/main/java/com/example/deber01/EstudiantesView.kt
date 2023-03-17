package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class EstudiantesView : AppCompatActivity() {
    var materias = ArrayList<Estudiantes>()
    var idSelecionado = 0
    var modo = "crud"
    val contenidoIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val resultado = data.getStringExtra("resultado")
                    if (resultado == "OK") {
                        cargarMaterias()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes_view)

        modo = intent.getStringExtra("modo").toString()
        var id = intent.getIntExtra("id", -1)

        var btnCrearMateria = findViewById<Button>(R.id.btn_crear_estudiante)

        when (modo) {
            "crud" -> {
                title = "Estudiantes"
                btnCrearMateria.setOnClickListener {
                    abrirActividadParametros(AdministrarEstudiantes::class.java, "crear", 0)
                }
                cargarMaterias()
            }
            "ver" -> {
                var nombreFacultad = CRUDsqLite(this).leerFacultad(id).nombreFacultad
                title = "Estudiantes de $nombreFacultad"
                btnCrearMateria.text = "Inscribir"

                val etIdInscribir = findViewById<EditText>(R.id.et_id_inscribir)
                etIdInscribir.visibility = View.VISIBLE

                btnCrearMateria.setOnClickListener {
                    val idMateria = etIdInscribir.text.toString().toInt()

                    if (CRUDsqLite(this).existEstudianteId(idMateria) && !CRUDsqLite(this).existInsripcion(
                            id,
                            idMateria
                        )
                    ) {
                        CRUDsqLite(this).crearInscripcion(id, idMateria)
                    } else {
                        Toast.makeText(
                            this,
                            "No existe el estudiante / Estudiante ya matriculado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    cargarEstudiantesFacultad(id)
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
        idSelecionado = materias[info.position].id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_estudiante_editar -> {
                abrirActividadParametros(
                    AdministrarEstudiantes::class.java, "editar", idSelecionado
                )
                if (modo == "crud") {
                    cargarMaterias()
                } else {
                    cargarEstudiantesFacultad(idSelecionado)
                }
                true
            }
            R.id.menu_estudiante_eliminar -> {
                CRUDsqLite(this).eliminarEstudiante(idSelecionado)
                if (modo == "crud") {
                    cargarMaterias()
                } else {
                    cargarEstudiantesFacultad(idSelecionado)
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun cargarEstudiantesFacultad(id: Int) {
        val listView = findViewById<ListView>(R.id.lv_materia)
        materias = CRUDsqLite(this).leerInscripcion(id)
        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, materias
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            abrirActividadParametros(
                AdministrarEstudiantes::class.java, "ver", materias[position].id
            )
        }
    }

    fun cargarMaterias() {
        val listView = findViewById<ListView>(R.id.lv_materia)
        materias = CRUDsqLite(this).leerEstudiantes()
        val adaptador = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, materias
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            abrirActividadParametros(
                AdministrarEstudiantes::class.java, "ver", materias[position].id
            )
        }
    }

    fun abrirActividadParametros(clase: Class<*>, modo: String, id: Int) {
        val intentExplicito = Intent(
            this, clase
        )
        intentExplicito.putExtra("modo", modo)
        intentExplicito.putExtra("id", id)
        contenidoIntent.launch(intentExplicito)
    }
}