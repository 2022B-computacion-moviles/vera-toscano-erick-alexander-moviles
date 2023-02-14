package com.example.movcompeavt

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()

        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Erick", "a@a.com")
                )

            arregloBEntrenador
                .add(
                    BEntrenador(2,"Alexander", "b@b.com")
                )

            arregloBEntrenador
                .add(
                    BEntrenador(3,"Johana", "c@c.com")
                )
        }

    }
}