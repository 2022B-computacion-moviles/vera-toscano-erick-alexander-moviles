
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

    // ARREGLOS

    // Tipos de Arreglos

    // Arreglo Estático

    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    println(arregloEstatico)

    // Arreglos Dinamicos
    var arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    //OPERADORES -> Sirven para los arreglos estáticos y dinámicos
    // FOR EACH -> unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach{
            valorActual: Int ->
            println("Valor actual: ${valorActual}")

    }
    arregloEstatico
        .forEachIndexed{indice: Int, valorActual: Int ->
            println("Valor: ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //MAP -> muta el arreglo (Cambia el arreglo)
    //1) Enviemos el nuevo valor a la iteracion
    //2) nos devuelve el Nuevo Arreglo con los valores modificados

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)

    //filter -> Filtar el arreglo
    // 1) devolver una expresion (True o False)
    // 2) Nuevo arreglo filtrado

    val respuestaFilter: List <Int> = arregloDinamico
        .filter{ valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5 // Expresion condicion
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <=5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR --< ANY (aLGUNO CUMPLE?)
    //AND -< ALL (Todos cumplen?)

    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual: Int ->
            return@any (valorActual>5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //false

    //REDUCE -> VALOR ACUMULADO
    // Valor acumulado = 0 (Siempre 0 en lenguaje kotlin)
    // [1, 2, 3 ,4 ,5] -> sumeme todos los valores del arreglo
    //valorIteracion1=valorEmpieza + 1 = 0+1=1 -> iteracion1
    //valorIteracion2 =

    val respuestaReduce: Int = arregloDinamico
        .reduce { // acumulado = 0 -> siempre empieza en 0
            acumulado : Int, valorActual:Int ->
            return@reduce(acumulado + valorActual) // logicaNegocio
        }

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