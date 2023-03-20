package com.example.deber01

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CRUDsqLite(
    context: Context?,
) : SQLiteOpenHelper(context, "facultad.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS Facultad (id INTEGER PRIMARY KEY AUTOINCREMENT, nombreFacultad TEXT, siglasFacultad TEXT, numEdificio INTEGER)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS Estudiante (id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT, apellidos TEXT, direccion TEXT, quintil INTEGER)")
        db?.execSQL("CREATE TABLE IF NOT EXISTS Inscripcion (id INTEGER PRIMARY KEY AUTOINCREMENT, idFacultad INTEGER, idEstudiante INTEGER, FOREIGN KEY(idFacultad) REFERENCES Facultad(id), FOREIGN KEY(idEstudiante) REFERENCES Estudiante(id))")
    }

    fun dropTables() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS Facultad")
        db.execSQL("DROP TABLE IF EXISTS Estudiante")
        db.execSQL("DROP TABLE IF EXISTS Inscripcion")
        onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Facultad")
        onCreate(db)
    }

    fun crearFacultad(facultad: Facultades) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO Facultad (nombreFacultad, siglasFacultad, numEdificio) VALUES ('${facultad.nombreFacultad}', '${facultad.siglas}', ${facultad.numEdificio})")
    }

    fun leerFacultad(id: Int): Facultades {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Facultad WHERE id = $id", null)
        cursor.moveToFirst()
        val estudiantes =
            if (cursor.count > 0) leerInscripcion(cursor.getInt(0)) else ArrayList()
        val facultades = Facultades(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getInt(3),
            estudiantes
        )
        cursor.close()
        db.close()
        return facultades
    }

    fun leerFacultades(): ArrayList<Facultades> {
        val facultades = ArrayList<Facultades>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Facultad", null)
        if (cursor.moveToFirst()) {
            do {
                val estudiantes = leerInscripcion(cursor.getInt(0))
                val facultad = Facultades(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    estudiantes
                )
                facultades.add(facultad)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return facultades
    }

    fun actualizarFacultad(facultad: Facultades) {
        val db = this.writableDatabase
        db.execSQL("UPDATE Facultad SET nombreFacultad = '${facultad.nombreFacultad}', siglasFacultad = '${facultad.siglas}', numEdificio = ${facultad.numEdificio} WHERE id = ${facultad.id}")
    }

    fun eliminarFacultad(id: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Inscripcion WHERE idFacultad = $id")
        db.execSQL("DELETE FROM Facultad WHERE id = $id")
    }

    fun leerInscripcion(idFacultad: Int): ArrayList<Estudiantes> {
        val estudiantes = ArrayList<Estudiantes>()
        val db = this.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM Inscripcion WHERE idFacultad = $idFacultad", null)
        if (cursor.moveToFirst()) {
            do {
                val estudiante = leerEstudiante(cursor.getInt(2))
                estudiantes.add(estudiante)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return estudiantes
    }

    fun crearEstudiante(estudiante: Estudiantes) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO Estudiante (nombres, apellidos, direccion, quintil) VALUES ('${estudiante.nombres}', '${estudiante.apellidos}', '${estudiante.direccion}', '${estudiante.quintil}')")
    }

    fun existEstudianteId(id: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estudiante WHERE id = $id", null)
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    fun existInsripcion(idFacultad: Int, idEstudiante: Int): Boolean {
        val db = this.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT * FROM Inscripcion WHERE idFacultad = $idFacultad AND idEstudiante = $idEstudiante",
                null
            )
        if (cursor.moveToFirst()) {
            return true
        }
        cursor.close()
        db.close()
        return false
    }

    fun leerEstudiante(id: Int): Estudiantes {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estudiante WHERE id = $id", null)
        if (cursor.moveToFirst()) {
            do {
                val estudiante = Estudiantes(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                )
                return estudiante
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return Estudiantes(-1, "", "", "", -1)
    }

    fun leerEstudiantes(): ArrayList<Estudiantes> {
        val estudiantes = ArrayList<Estudiantes>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estudiante", null)
        if (cursor.moveToFirst()) {
            do {
                var x = cursor.getString(1);
                val estudiante = Estudiantes(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                )
                estudiantes.add(estudiante)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return estudiantes
    }

    fun actualizarEstudiante(estudiante: Estudiantes) {
        val db = this.writableDatabase
        db.execSQL("UPDATE Estudiante SET nombres = '${estudiante.nombres}', apellidos = '${estudiante.apellidos}', direccion = '${estudiante.direccion}', quintil = ${estudiante.quintil} WHERE id = ${estudiante.id}")
    }

    fun eliminarEstudiante(id: Int) {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM Inscripcion WHERE idEstudiante = $id")
        db.execSQL("DELETE FROM Estudiante WHERE id = $id")
    }

    fun crearInscripcion(idFacultad: Int, idEstudiante: Int) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO Inscripcion (idFacultad, idEstudiante) VALUES ($idFacultad, $idEstudiante)")
    }
}