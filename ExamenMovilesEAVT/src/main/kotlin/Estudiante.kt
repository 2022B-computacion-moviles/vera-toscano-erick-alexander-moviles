import java.util.*


class Estudiante(
    var id: Int,
    var nombre: String,
    var apellido: String,
    var direccion: String,
) {

    constructor(data: List<String>) : this(
        data[0].toInt(),
        data[1],
        data[2],
        data[3],
    )


    override fun toString(): String {
        return "$id,$nombre,$apellido,$direccion"
    }

}