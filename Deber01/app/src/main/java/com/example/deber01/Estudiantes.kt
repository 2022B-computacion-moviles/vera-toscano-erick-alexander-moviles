package com.example.deber01

class Estudiantes(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val direccion: String,
    val quintil: Int,
) {
    override fun toString(): String {
        return "$id | $nombres | $apellidos | $direccion | $quintil";
    }
}