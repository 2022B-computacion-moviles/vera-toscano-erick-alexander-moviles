import java.util.*

class Facultad(
    var id: Int,
    var nombre: String,
    var codigo: String,
    var numEdificio: String,
    var estudiantes: ArrayList<Estudiante>
) {

    constructor(data: List<String>) : this(
        data[0].toInt(),
        data[1],
        data[2],
        data[3],
        ArrayList()
    ){
        data[4].split("-").forEach { idEstudiante ->
            if (idEstudiante != "") {
                val ma = CRUD.leerEstudiante(idEstudiante.toInt());
                if (ma.id != -1) {
                    estudiantes.add(ma)
                }
            }
        }
    }



    override fun toString(): String {
        var idEstudiantes=""
        estudiantes.forEach{
            estudiante ->
            idEstudiantes+=estudiante.id.toString()+"-"
        }
        return "$id,$nombre,$codigo,$numEdificio,$idEstudiantes";
    }

    fun stringFacultad(): String {
        var estudiantesInscritos = "Estudiantes inscritos:\n " +
                "ID\tNOMBRE\tAPELLIDO\tDIRECCION\n";
        estudiantes.forEach { estudiante ->
            estudiantesInscritos +=estudiante.toString().replace(',', '\t') + "\n";
        }
        return "$id\t$nombre\t$codigo\t$numEdificio\n$estudiantesInscritos"
    }
}
