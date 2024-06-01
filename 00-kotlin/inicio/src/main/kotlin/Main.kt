import java.util.*
fun main() {
    println("Hola mundo")

    //Inmutable (No se puede RE ASIGNAR "=")
    val inmutable: String = "Dario";
    //inmutable = "Vicente" // Error !
    // Mutables
    var mutable: String = "Vicente"
    mutable = "Adrian" // ok
    // VAL > VAR: Siempre que se cree una variable, de preferencia crearla de manera inmutable, no variable

    // Duck Typing
    var ejemploVariable = " Dario Charro "
    val edadEjemplo: Int = 12
    ejemploVariable.trim() //Quitar espacios en blanco
    // ejemploVariable = edadEjemplo // ERROR ! tipo incorrecto

    // Variable primitivas
    val nombre:String = "Dario"
    val sueldo:Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // Clases en Java
    val fechaNacimiento: Date = Date()

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito



    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa=14.00)

    //Uso de clases
    val sumaUno = Suma(1,1) //new Suma(1,1) en KOTLIN no hay "new"
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos
    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    //Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11) // En un arreglo estatico NO es posible el metodo add
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH = > Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach{valorActual: Int -> //Este forEach pertenece al arregloDinamico
            println("Valor actual: ${valorActual}")
        }
    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{println("Valor Actual (it): ${it}")}

    //MAP -> MUTA(Modifica o cambia) el arreglo
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve un NUEVO ARREGLO con valores de las iteraciones

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
            //@map sintaxis de kotlin para indicar un return de dicha función map
            //En otro caso se debe cambiar por el nombre de la funcion
            //Es para indicar el return de la función que se está usando: funcion pepito -> @pepito
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map{it + 15}
    println(respuestaMapDos)


    // FILTER -> Filtrar el ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico
        .filter{ valorActual:Int ->
            //Expresion o CONDICIÓN
            val mayoresACinco:Boolean = valorActual > 5


            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter{it <= 5}
    println(respuestaFilter)
    println(respuestaFilterDos)

    // OR AND
    // OR -> ANY (Alguno Cumple?)
    // AND -> ALL (Todos Cummplen?)
    val respuestaAny:Boolean = arregloDinamico
        .any{ valorActual:Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //True

    val respuestaAll:Boolean = arregloDinamico
        .all{ valorActual:Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll) //False

    // REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre empieza en 0 en Kotlin)
    // [1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 -> Iteracion1
    // valorIteracion2 = valorAcumuladoIteracio1 + 2 = 1 + 2 = 1 -> Iteracion2
    // valorIteracion3 = valorAcumuladoIteracio2 + 3 = 3 + 3 = 1 -> Iteracion3
    // valorIteracion4 = valorAcumuladoIteracio3 + 4 = 6 + 4 = 1 -> Iteracion4
    // valorIteracion5 = valorAcumuladoIteracio4 + 5 = 10 + 5 = 1 -> Iteracion5

    val respuestaReduce:Int = arregloDinamico
        .reduce{ acumulado:Int, valorActual:Int ->
            return@reduce (acumulado + valorActual)
        }
    println(respuestaReduce)



}

// void -> Unit
fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}") // Template Strings
}
fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial:Double? = null // Opcional (nullable)
    // Variable? -> "?" Es Nullable (osea que puede en algun momento ser nulo)
):Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int , (parametro (sin modificador acceso))

    // Caso 2) Parametro y propiedad (atributo) (private)
    // private var uno: Int (propiedad "instancia.uno")

    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    // parametroInutil:String , // Parametro
){
    init { // bloque constructor primario (OPCIONAL)
        this.numeroUno
        this.numeroDos
        // this.parametroInutil //  ERROR NO EXISTE
        println("Inicializando")
    }
}


class Suma( // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
): Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito:String = "Explicito" // Publicas
    val soyPublicoImplicito:String = "Implicito" // Publicas (propiedades, metodos)
    init{ // Bloque Codigo Constructor primario
        // this.unoParametro // ERROR no existe
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL (propiedades, metodos)
        numeroDos // this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
    }
    constructor( //Constructores secundario
        uno: Int?, // ? es que sea algun momento es nulleable
        dos: Int
    ):this(
        if(uno==null) 0 else uno,
        dos
    )

    constructor( //Constructor tercero
        uno: Int, // ? es que sea algun momento es nulleable
        dos: Int?
    ):this(
        if(dos==null) 0 else dos,
        uno
    )

    constructor( //Constructor cuarto
        uno: Int?, // ? es que sea algun momento es nulleable
        dos: Int?
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos,
    )


    // public fun sumar()Int{ (Modificar "public" es OPCIONAL
    fun sumar():Int{
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }
    companion object{ // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}
