package com.example.movcompeavt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ESqliteHelperEntrenador(contexto: Context?) : SQLiteOpenHelper(
    contexto,
    "moviles", //Nombre de nuestra BDD SqLite (moviles.sqlite)
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
                
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //funcion para crear entrenadores
    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        //this.readableDatabase Lectura
        //this.writableDatabase Escritura
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar = basedatosEscritura.insert(
            "ENTRENADOR", //tabla
            null,
            valoresAGuardar //valores
        )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    /*Función para eliminar entrenadores*/

    fun eliminarEntrenadorFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        /* Estructura de select bdd
        "SELECT * FROM ENTRENADOR WHERE ID = ?"
        arrayof(
        id.toString()
        )*/
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Entrenador", //tabla
                "id=?", // id=? and nombre? Where (podemos mandar parametros en orden)
                arrayOf( // arreglo parametros en orden [1, "Adrian"])
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    /*Función para actualizar entrenadores*/

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", //nombre tabla
                valoresActualizar, //valores a actualizar
                "id=?", //clausula where
                arrayOf(
                    idActualizar.toString()
                ) //parametros clausula where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    /*Función para consultar entrenadores por id*/

    fun consultarEntrenadorPorId(id: Int): BEntrenador {
        // val BaseDatosLectura = this.readableDatabase
        val baseDatosLectura = readableDatabase
        val scriptConsultaUsuario = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaUsuario,
            arrayOf(
                id.toString()
            )
        )
        //logica de busqueda
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        var usuarioEncontrado =
            BEntrenador(0, "", "") //usuario con datos vacios por si no se encuentra al entrenador
        //logica obtener el usuario
        do {
            val id = resultadoConsultaLectura.getInt(0)//columna indice 0 -> ID
            val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
            val descripcion = resultadoConsultaLectura.getString(2) //Columna indice 2 -> DESCRIPCION
            if (id != null) {
                usuarioEncontrado.id = id
                usuarioEncontrado.nombre = nombre
                usuarioEncontrado.descripcion = descripcion
            }
        } while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

}