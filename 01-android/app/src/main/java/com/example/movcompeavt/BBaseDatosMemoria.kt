package com.example.movcompeavt

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador("Erick", "a@a.com")
                )

            arregloBEntrenador
                .add(
                    BEntrenador("Alexander", "b@b.com")
                )

            arregloBEntrenador
                .add(
                    BEntrenador("Johana", "c@c.com")
                )
        }

    }
}