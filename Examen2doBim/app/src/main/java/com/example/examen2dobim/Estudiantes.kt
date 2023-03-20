package com.example.examen2dobim

class Estudiantes(
    val id: String,
    val nombres: String,
    val apellidos: String,
    val direccion: String,
    val quintil: Int,
) {
    override fun toString(): String {
        return "$nombres | $apellidos | $direccion | $quintil";
    }
}