package com.example.examen2dobim

import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList

class crudFirebase {


    fun crearFacultad(facultad: Facultades) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Facultad").add(facultad)
    }

    fun leerFacultad(
        id: String, callback: (Facultades) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        var facultad = Facultades("", "", "", 0, ArrayList())
        db.collection("Facultad").document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                var estudiantes = ArrayList<Estudiantes>()
                facultad = Facultades(
                    document.id,
                    document.data!!["nombreFacultad"].toString(),
                    document.data!!["siglas"].toString(),
                    document.data!!["numEdificio"].toString().toInt(),
                    estudiantes
                )
                callback(facultad)
            }
        }
    }

    fun leerFacultades(callback: (ArrayList<Facultades>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val facultades = ArrayList<Facultades>()
        db.collection("Facultad").get().addOnSuccessListener { result ->
            for (document in result) {
                var estudiantes = ArrayList<Estudiantes>()
                val facultad = Facultades(
                    document.id,
                    document.data!!["nombreFacultad"].toString(),
                    document.data!!["siglas"].toString(),
                    document.data!!["numEdificio"].toString().toInt(),
                    estudiantes
                )
                facultades.add(facultad)
            }
            callback(facultades)
        }
    }

    fun actualizarFacultad(facultad: Facultades) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Facultad").document(facultad.id).set(facultad)
    }

    fun eliminarFacultad(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Facultad").document(id).delete()
        db.collection("Inscripcion").whereEqualTo("idFacultad", id).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection("Inscripcion").document(document.id).delete()
                }
            }
    }

    fun leerInscripcion(id: String, callback: (ArrayList<String>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val estudiantesIds = ArrayList<String>()
        db.collection("Inscripcion").whereEqualTo("idFacultad", id).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    estudiantesIds.add(document.data["idEstudiante"].toString())
                }
                callback(estudiantesIds)
            }
    }

    fun crearEstudiante(estudiante: Estudiantes) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Estudiante").add(estudiante)
    }


    fun existInsripcion(idFacultad: String, idEstudiante: String, callback: (Boolean) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        var exist = false
        db.collection("Inscripcion").whereEqualTo("idFacultad", idFacultad)
            .whereEqualTo("idEstudiante", idEstudiante).get().addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        exist = true
                    }
                    callback(exist)
                }
            }
    }

    fun leerEstudiante(id: String, callback: (Estudiantes) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        var estudiante = Estudiantes("", "", "", "", 0)
        db.collection("Estudiante").document(id).get().addOnSuccessListener { document ->
            if (document != null) {
                estudiante = Estudiantes(
                    document.id,
                    document.data!!["nombres"].toString(),
                    document.data!!["apellidos"].toString(),
                    document.data!!["direccion"].toString(),
                    document.data!!["quintil"].toString().toInt(),
                )
                callback(estudiante)
            }
        }
    }

    fun leerEstudiantes(callback: (ArrayList<Estudiantes>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val estudiantes = ArrayList<Estudiantes>()
        db.collection("Estudiante").get().addOnSuccessListener { result ->
            for (document in result) {
                val estudiante = Estudiantes(
                    document.id,
                    document.data["nombres"].toString(),
                    document.data["apellidos"].toString(),
                    document.data["direccion"].toString(),
                    document.data["quintil"].toString().toInt()
                )
                estudiantes.add(estudiante)
            }
            callback(estudiantes)
        }
    }

    fun actualizarEstudiante(estudiante: Estudiantes) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Estudiante").document(estudiante.id.toString()).set(estudiante)
    }

    fun eliminarEstudiante(id: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("Estudiante").document(id.toString()).delete()
        db.collection("Inscripcion").whereEqualTo("idEstudiante", id).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection("Inscripcion").document(document.id).delete()
                }
            }
    }

    fun crearInscripcion(idFacultad: String, idEstudiante: String) {
        val db = FirebaseFirestore.getInstance()
        val inscripcion = hashMapOf(
            "idFacultad" to idFacultad, "idEstudiante" to idEstudiante
        )
        db.collection("Inscripcion").add(inscripcion)
    }


}