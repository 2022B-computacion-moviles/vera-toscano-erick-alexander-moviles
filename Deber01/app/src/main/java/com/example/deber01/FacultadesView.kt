package com.example.deber01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts

class FacultadesView : AppCompatActivity() {
    var idSeleccionado = 0
    var facultades = ArrayList<Facultades>()

    val contenidoIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val resultado = data.getStringExtra("resultado")
                    if (resultado == "OK") {
                        cargarFacultades()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facultades_view)
        // Change title
        title = "Facultades"

        //DatabaseHelper(this).dropTables()
        // ESTUDIANTES
        val btnCrearEstudiante = findViewById<Button>(R.id.btn_crear_facultad)
        facultades = CRUDsqLite(this).leerFacultad()

        btnCrearEstudiante.setOnClickListener {
            abrirActividadParametros(AdministrarFacultades::class.java, "crear", 0)
        }

        cargarFacultades()

        // Estudiantes
        val btnEstudiantes = findViewById<Button>(R.id.btn_estudiantes)
        btnEstudiantes.setOnClickListener {
            abrirActividadParametros(EstudiantesView::class.java, modo = "crud", id = 0)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_facultades, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSeleccionado = facultades[info.position].id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_facultad_editar -> {
                abrirActividadParametros(
                    AdministrarFacultades::class.java,
                    "editar",
                    idSeleccionado
                )
                true
            }
            R.id.menu_facultad_eliminar -> {
                CRUDsqLite(this).eliminarFacultad(idSeleccionado)
                cargarFacultades()
                true
            }
            R.id.menu_facultad_ver -> {
                abrirActividadParametros(
                    EstudiantesView::class.java,
                    "ver",
                    idSeleccionado
                )
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun cargarFacultades() {
        val listView = findViewById<ListView>(R.id.lv_facultades)
        facultades = CRUDsqLite(this).leerFacultad()
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            facultades
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            abrirActividadParametros(
                AdministrarFacultades::class.java,
                "ver",
                facultades[position].id
            )
        }
    }

    fun abrirActividadParametros(clase: Class<*>, modo: String, id: Int) {
        val intentExplicito = Intent(
            this,
            clase
        )
        intentExplicito.putExtra("modo", modo)
        intentExplicito.putExtra("id", id)
        contenidoIntent.launch(intentExplicito)
    }
}