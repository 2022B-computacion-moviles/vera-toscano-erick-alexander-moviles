package com.example.movcompeavt

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context?,
) : SQLiteOpenHelper(
    contexto,
    "moviles", // Nombre de nuestra BDD Sqlite (moviles.sqlite)
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

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        // this.readableDatabase Lectura
        // this.writableDatabase Escritura
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Tabla
                null, //
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id: Int): Boolean {
        //        val conexionEscritura = this.writableDatabase
        val conexionEscritura = writableDatabase
        // "SELECT * FROM ENTRENADOR WHERE ID = ?"
        // arrayOf(
        //    id.toString()
        // )
        val resultadoEliminacion = conexionEscritura
            .delete(
                "ENTRENADOR", // TABLA
                "id=?", //  id=? and nombre=? Where (podemos mandar parametros en orden)
                arrayOf( // Arreglo parametros en orden [1,"Adrian"]
                    id.toString()
                )
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarEntrenadorPorId(id:Int): BEntrenador{
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
        var usuarioEncontrado = BEntrenador(0, "", "")
        val arreglo = arrayListOf<BEntrenador>()
        if(existeUsuario){
            do{
                val id = resultadoConsultaLectura.getInt(0)//columna indice 0 -> ID
                val nombre = resultadoConsultaLectura.getString(1) // Columna indice 1 -> NOMBRE
                val descripcion =
                    resultadoConsultaLectura.getString(2) //Columna indice 2 -> DESCRIPCION
                if (id != null){
                    usuarioEncontrado = BEntrenador(0,"","")
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    arreglo.add(usuarioEncontrado)
                }
            } while (resultadoConsultaLectura.moveToNext())
        }

        //logica obtener el usuario
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}