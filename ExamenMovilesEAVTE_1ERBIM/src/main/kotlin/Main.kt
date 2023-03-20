import java.util.ArrayList

fun main() {
    println("*******Administrador de estudiantes y facultades*******\n")

    println("Eliga una opcion:\n" +
            "1. Facultad\n" +
            "2. Estudiante\n" +
            "0. Salir\n")

    print("Ingrese su opción: ");
    val option = readLine()!!.toInt();

    when(option){
        1 -> {
            facultadMenu()
        }
        2 -> {
            estudianteMenu()
        }
        0 -> {
            return
        }
        else -> {
            println("Opcion invalida")
            main()
        }
    }
}

/**
 menu de estudiantes
 */
fun estudianteMenu(): Unit {
    println("*******MENU ESTUDIANTES*******\n")

    println("Eliga una opcion:\n" +
            "1. Crear estudiante\n" +
            "2. Listar estudiantes\n" +
            "3. Buscar estudiante\n" +
            "4. Actualizar estudiante\n" +
            "5. Eliminar estudiante\n" +
            "0. Menu anterior\n")

    print("Ingrese su opción: ");
    val option = readLine()!!.toInt();

    when(option){
        1 -> estudianteCreate()
        2 -> estudianteList()
        3 -> estudianteSearch()
        4 -> estudianteUpdate()
        5 -> estudianteDelete()
        0 -> main()
        else -> {
            println("Opcion invalida\n")
            estudianteMenu()
        }
    }

}
/**
 * Menu para facultades
*/
fun facultadMenu(): Unit {
    println("*******MENU FACULTADES*******\n")

    println("Eliga una opcion:\n" +
            "1. Ingresar facultad\n" +
            "2. Listar facultades\n" +
            "3. Buscar facultad\n" +
            "4. Actualizar facultad\n" +
            "5. Eliminar facultad\n" +
            "6. Inscribir Estudiante\n"+
            "0. Menu principal\n"

    )
    print("Ingrese su opción: ");
    val option = readLine()!!.toInt();

    when(option){
        1 -> ingresarFacultad()
        2 -> listarFacultades()
        3 -> buscarFacultad()
        4 -> actualizarFAcultad()
        5 -> eliminarFacultad()
        6 -> inscribirEstudiante()
        0 -> main()
        else -> {
            println("Opcion invalida\n")
            facultadMenu()
        }
    }
}

fun estudianteCreate(): Unit {
    println("--------------------------------------------------\n" +
            "                 CREAR ESTUDIANTE                 \n" +
            "--------------------------------------------------")
    print("Ingrese el nombre del estudiante: ");
    val nombre = readLine()!!;
    print("Ingrese el apellido del estudiante: ");
    val apellido = readLine()!!;
    print("Ingrese la direccion del estudiante: ");
    val direccion = readLine()!!;


    val id = CRUD.listarEstudiantes().size;
    val estudiante = Estudiante(
        id = id,
        nombre = nombre,
        apellido = apellido,
        direccion = direccion,
    );

    if (CRUD.anadirEstudiante(estudiante))
        println("\nEstudiante creado exitosamente");

    estudianteMenu()
}

fun estudianteList(): Unit {
    println("*******LISTAR ESTUDIANTES*******\n")

    val estudiantes = CRUD.listarEstudiantes();
    println("ID\tNOMBRE\tAPELLIDO\tDIRECCION");
    estudiantes.forEach { estudiante ->
        println(estudiante.toString().replace(',', '\t'));
    }
    estudianteMenu()
}

fun estudianteSearch(): Unit {
    println("*******BUSCAR ESTUDIANTE*******\n")

    print("Ingrese el ID del estudiante: ");
    val id = readLine()!!;
    val estudiante = CRUD.leerEstudiante(id.toInt());
    if (estudiante.id == -1) {
        println("\nNo se encontro el estudiante\n");
        return;
    }
    println("ID\tNOMBRE\tAPELLIDO\tDIRECCION");
    println(estudiante.toString().replace(',', '\t'));
    estudianteMenu()
}

fun estudianteUpdate(): Unit {
    println("*******ACTUALIZAR ESTUDIANTE*******\n")
    print("Ingrese el ID del estudiante: ");
    val id = readLine()!!

    print("Ingrese el nombre del estudiante: ");
    val nombre = readLine()!!;
    print("Ingrese el apellido del estudiante: ");
    val apellido = readLine()!!;
    print("Ingrese la direccion del estudiante: ");
    val direccion = readLine()!!;
    print("Ingrese el telefono del estudiante: ");
    val estudiante = Estudiante(
        id = id.toInt(),
        nombre = nombre,
        apellido = apellido,
        direccion = direccion,
    );
    if (CRUD.actualizarEstudiante(estudiante))
        println("Estudiante actualizado exitosamente");

    estudianteMenu()
}

fun estudianteDelete(): Unit {

    println("*******ELIMINAR ESTUDIANTE*******\n")

    print("Ingrese el ID del estudiante: ");
    val id = readLine()!!;


    if (CRUD.eliminarEstudiante(id.toInt()))
        println("Estudiante eliminado exitosamente");

    estudianteMenu()
}





fun ingresarFacultad(): Unit {
    println("*******CREAR FACULTAD*******\n")

    print("Ingrese el nombre de la facultad: ");
    val nombre = readLine()!!;
    print("Ingrese el codigo de la facultad: ");
    val codigo = readLine()!!;
    print("Ingrese el edificio de la facultad: ");
    val nEdificio = readLine()!!;

    val id = CRUD.listarFacultades().size;
    val facultad = Facultad(
        id = id,
        nombre = nombre,
        codigo = codigo,
        numEdificio = nEdificio,
        estudiantes = ArrayList(),
    );

    if (CRUD.createFacultad(facultad))
        println("Facultad creada exitosamente");

    facultadMenu()
}


fun listarFacultades(): Unit {

    println("*******LISTA DE FACULTADES Y ESTUDIANTES MATRICULADOS*******\n")

    val facultades = CRUD.listarFacultades();
    println("ID\tNOMBRE\tCODIGO");
    facultades.forEach { facultad ->
        println(facultad.stringFacultad());
    }
    facultadMenu()
}

fun buscarFacultad(): Unit {

    println("*******BUSCAR FACULTAD*******\n")

    print("Ingrese el ID de la facultad: ");
    val id = readLine()!!;
    val facultad = CRUD.leerFacultad(id.toInt());
    println("ID\tNOMBRE\tCODIGO\tCREDITOS\tCOSTO CREDITO");
    println(facultad.stringFacultad());
    facultadMenu()
}

fun actualizarFAcultad(): Unit {

    println("*******ACTUALIZAR FACULTAD*******\n")

    print("Ingrese el ID de la facultad: ");
    val id = readLine()!!;

    print("Ingrese el nombre de la facultad: ");
    val nombre = readLine()!!;
    print("Ingrese el codigo de la facultad: ");
    val codigo = readLine()!!;
    print("Ingrese el numero de edificio de la facultad: ");
    val nEdificio = readLine()!!;

    val facultad = Facultad(
        id = id.toInt(),
        nombre = nombre,
        codigo = codigo,
        numEdificio = nEdificio,
        estudiantes = ArrayList(),
    );

    if (CRUD.actualizarFacultad(facultad))
        println("Facultad actualizada exitosamente");

    facultadMenu()
}

fun eliminarFacultad(): Unit {
    println("*******ELIMINAR FACULTAD*******\n")

    print("Ingrese el ID de la facultad: ");
    val id = readLine()!!;

    if (CRUD.eliminarFacultad(id.toInt()))
        println("Facultad eliminada exitosamente");

    facultadMenu()
}

fun inscribirEstudiante(): Unit {

    println("*******AGREGAR ESTUDIANTE A FACULTAD*******\n")

    print("Ingrese el ID de la facultad: ");
    val idFacultad = readLine()!!;

    print("Ingrese el ID del estudiante: ");
    val idEstudiante = readLine()!!;


    if (CRUD.anadirEstudiateFacultad(idFacultad.toInt(), idEstudiante.toInt()))
        println("Estudiante agregado exitosamente a la facultad");

    facultadMenu()
}
