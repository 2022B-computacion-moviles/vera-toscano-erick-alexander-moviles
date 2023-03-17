package com.example.deber01

import java.text.SimpleDateFormat
import java.util.*

class Facultades(
    val id: Int,
    val nombreFacultad: String,
    val siglas: String,
    var numEdificio: Int,
    var estudiantes: ArrayList<Estudiantes>,
) {
    override fun toString(): String {
        return "$id | $nombreFacultad | $siglas | $numEdificio"
    }
}