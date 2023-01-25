import java.io.File
import kotlin.collections.ArrayList

class CRUD {
    companion object {

        fun crearArchivo(name: String): Unit {
            val file = File(name);

            if (!file.exists()) {
                file.createNewFile();
            }
        }


        fun listarCSV(data: String): List<List<String>> {
            val lines = data.split("\n");
            val result = mutableListOf<List<String>>();
            for (line in lines) {
                if (line.isNotEmpty()) {
                    result.add(line.split(","));
                }
            }
            return result;
        }

        fun formatoCSVEstudiante(Estudiante: MutableList<Estudiante>?): String {
            val es = Estudiante;
            var result = "";
            for (line in es!!) {
                result += line.toString() + "\n";
            }
            return result;
        }

        fun formatoCSVFacultad(Facultad: MutableList<Facultad>?): String {
            val fa = Facultad;
            var result = "";
            for (line in fa!!) {
                result += line.toString() + "\n";
            }
            return result;
        }


        fun leerArchivo(name: String): String {
            crearArchivo(name);
            val file = File(name);
            return file.readText();
        }

        fun escribirArchivo(name: String, data: String): Unit {
            crearArchivo(name);
            val file = File(name);
            file.writeText(data);
        }



        fun anadirEstudiante(estudiante: Estudiante): Boolean {
            val data = leerArchivo("estudiantes.csv");
            val estudiantes = listarCSV(data).map { Estudiante(it) }.toMutableList();
            estudiantes.add(estudiante);
            escribirArchivo("estudiantes.csv", formatoCSVEstudiante(estudiantes));
            return true;
        }

        fun leerEstudiante(id: Int): Estudiante {
            val data = leerArchivo("estudiantes.csv");
            val estudiantes = listarCSV(data).map { Estudiante(it) }.toMutableList();
            val R = estudiantes.find { estudiante -> estudiante.id == id };
            if (R != null) {
                return R;
            }
            return Estudiante(-1, "", "", "");
        }

        fun listarEstudiantes(): List<Estudiante> {
            val data = leerArchivo("estudiantes.csv");
            val estudiantes = listarCSV(data).map { Estudiante(it) }.toMutableList();
            return estudiantes;
        }

        fun actualizarEstudiante(estudiante: Estudiante): Boolean {
            val data = leerArchivo("estudiantes.csv");
            val estudiantes = listarCSV(data).map { Estudiante(it) }.toMutableList();
            val index = estudiantes.indexOfFirst { it.id == estudiante.id };
            estudiantes[index] = estudiante;
            escribirArchivo("estudiantes.csv", formatoCSVEstudiante(estudiantes));
            return true;
        }

        fun eliminarEstudiante(id: Int): Boolean {
            val data = leerArchivo("estudiantes.csv");
            val estudiantes = listarCSV(data).map { Estudiante(it) }.toMutableList();
            val index = estudiantes.indexOfFirst { it.id == id };
            estudiantes.removeAt(index);
            escribirArchivo("estudiantes.csv", formatoCSVEstudiante(estudiantes));
            return true;
        }


        fun createFacultad(facultad: Facultad): Boolean {
            val data = leerArchivo("facultades.csv");
            val facultades = listarCSV(data).map { Facultad(it) }.toMutableList();
            facultades.add(facultad);
            escribirArchivo("facultades.csv", formatoCSVFacultad(facultades));
            return true;
        }

        fun leerFacultad(id: Int): Facultad {
            val data = leerArchivo("facultades.csv");
            val facultades = listarCSV(data).map { Facultad(it) }.toMutableList();
            val R = facultades.find { facultad -> facultad.id == id };
            if (R != null) {
                return R;
            }
            return Facultad(-1, "", "", "", ArrayList());
        }

        fun listarFacultades(): List<Facultad> {
            val data = leerArchivo("facultades.csv");
            val facultades = listarCSV(data).map { Facultad(it) }.toMutableList();
            return facultades;
        }

        fun actualizarFacultad(facultad: Facultad): Boolean {
            val data = leerArchivo("facultades.csv");
            val facultades = listarCSV(data).map { Facultad(it) }.toMutableList();
            val index = facultades.indexOfFirst { it.id == facultad.id };
            facultades[index] = facultad;
            escribirArchivo("facultades.csv", formatoCSVFacultad(facultades));
            return true;
        }

        fun eliminarFacultad(id: Int): Boolean {
            val data = leerArchivo("facultades.csv");
            val facultades = listarCSV(data).map { Facultad(it) }.toMutableList();
            val index = facultades.indexOfFirst { it.id == id };
            facultades.removeAt(index);
            escribirArchivo("facultades.csv", formatoCSVFacultad(facultades));
            return true;
        }

        fun anadirEstudiateFacultad(idFacultad: Int, idEstudiante: Int): Boolean {
            val data = leerArchivo("facultades.csv");
            val facultades = listarCSV(data).map { Facultad(it) }.toMutableList();
            val index = facultades.indexOfFirst { it.id == idFacultad };
            facultades[index].estudiantes.add(listarEstudiantes().find { it.id == idEstudiante }!!);
            escribirArchivo("facultades.csv", formatoCSVFacultad(facultades));
            return true;
        }
    }
}