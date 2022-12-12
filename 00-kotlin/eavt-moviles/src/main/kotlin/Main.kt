
import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")
    // Tipos de varialbes
     // inmutables (No se pueden reasignar)
    //val ->inmuatable
    val inmutable: String ="Erick";

    //Mutables (re asignar)
    //var->mutable
    var mutable:String = "Erick"
    mutable = "Vera"

    //val>var

    //duck Typing: Permite declarar variables sin necesidad de declarar que tipo
    //de variable es, siempre que sea posible.

    val ejemploVariable = "Ejemplo";
    ejemploVariable.trim();
    val edadEjemplo: Int =12;

    //Variables primitivas
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil:Char = 'S'
    val mayorEdad=true

    //clases
    val fechaNacimiento: Date =Date() //No se usa "new" en clases

    //bucles
    if(true){}else{}
    //switch no existe
    val estadoCivilWhen = "S"
    when (estadoCivilWhen){
        ("S") -> {
            print("Soltero")
        }
        "C" -> println("Casado")
        else -> println("Desconocido")
    }
    val coqueto = if (estadoCivilWhen == "S") "Si" else "No"
    val sumaUno = Suma(1,2)
    val sumaDos = Suma(1, null)
    val sumaTres = Suma(null, 2)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.historialSumas)
}
fun imprimirNombre (nombre: String): Unit{ //No existe void, se puede usar unit
    println("Nombre: ${nombre}")
}
fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double= 12.00, //opcinal por defecto
    bonoEspecial: Double? = null //opcional nulo (?)
):Double{
    if (bonoEspecial !=null){
        return sueldo*tasa*bonoEspecial
    }
    return sueldo*tasa
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int, //parametro
        dos: Int //parametro
    ){
        this.numeroUno = uno;
        this.numeroDos= dos;
        println("Iniciando")
    }

}

abstract class Numeros ( // Constructror primario
    //uno: Int, //Parametro
    // public var uno: Int, //Propiedad de la clase publica
    protected val numeroUno: Int, //Propiedad
    protected val numeroDos: Int //Propiedad
){
    init{ //Bloque codigo constructor primario
        //this.numeroDos // "This" opcional
        //this.numeroUno
        numeroUno
        numeroDos
        println("Iniciando")
    }
}

class Suma( //Constructor Primario Suma
    uno: Int,
    dos: Int,
) : Numeros( //Heredamos la clase numeros
    // super constructor numeros
    uno,
    dos,
){
    init { //bloque constructor primario
        this.numeroUno
        this.numeroDos
    }
    constructor(//segundo constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ): this(
        if (uno == null) 0 else uno,
        dos
    ){}
    constructor(//tercer constructor
        uno: Int, //parametros
        dos: Int? //parametros
    ): this(
        uno,
        if (dos == null) 0 else dos,

    ){}
    constructor(//cuarto constructor
        uno: Int?, //parametros
        dos: Int? //parametros
    ): this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos,
        ){}
    fun sumar():Int{
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{
        val pi=3.14 //Suma.pi ->3.14
        val historialSumas = arrayListOf<Int>() //Suma.historialSumas
        fun agregarHistorial (valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }

}