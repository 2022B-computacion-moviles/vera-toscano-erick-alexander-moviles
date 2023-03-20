package com.example.examen2dobim

import java.util.*

class Facultades(
    val id: String,
    val nombreFacultad: String,
    val siglas: String,
    var numEdificio: Int,
    var estudiantes: ArrayList<Estudiantes>,
) {
    override fun toString(): String {
        return "$nombreFacultad | $siglas | $numEdificio"
    }
}