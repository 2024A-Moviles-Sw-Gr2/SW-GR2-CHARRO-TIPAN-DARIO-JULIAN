import java.io.File
import java.time.LocalDate
import kotlin.collections.ArrayList

class Habitacion(
    val codigoHabitacion: Int,
    val tipoHabitacion: String, // Por ejemplo: "Sencilla", "Doble", "Suite", etc.
    val precioNoche: Double,
    val ocupada: Boolean,
    val fechaUltimaOcupacion: LocalDate? = null
) {
    init {
        this.codigoHabitacion
        this.tipoHabitacion
        this.precioNoche
        this.ocupada
        this.fechaUltimaOcupacion
    }

    companion object {
        // Ruta del archivo para almacenar la información de las habitaciones
        private val archivoHabitaciones:File = File("src/main/habitaciones.txt")

        fun crearHabitacion(habitacion: Habitacion): Unit {
            println("\n---- CREACION DE HABITACION ----")
            val registro = "${habitacion.codigoHabitacion}, ${habitacion.tipoHabitacion}, ${habitacion.precioNoche}, ${habitacion.ocupada}, ${habitacion.fechaUltimaOcupacion ?: "N/A"}"
            archivoHabitaciones.appendText("\n$registro")
            println("Habitación registrada con éxito! [$registro]")
        }

        fun verHabitaciones(): Unit {
            println("\n---- HABITACIONES REGISTRADAS ----")
            println(archivoHabitaciones.readText())
        }

        fun eliminarHabitacion(codigoHabitacion: Int): Unit {
            println("\n---- ELIMINAR HABITACION ----")
            val lineas = archivoHabitaciones.readLines()
            val lineasActualizadas = lineas.filter { !it.contains(codigoHabitacion.toString()) }
            archivoHabitaciones.writeText(lineasActualizadas.joinToString("\n"))
            println("Habitación $codigoHabitacion ha sido eliminada")
        }

        fun actualizarHabitacion(

            codigoHabitacion: Int,
            tipoHabitacion: String? = null,
            precioNoche: Double? = null,
            ocupada: Boolean? = null,
            fechaUltimaOcupacion: LocalDate? = null
        ): Unit {
            println("\n---- ACTUALIZAR HABITACION ----")
            val lineas = archivoHabitaciones.readLines()
            var lineaActualizar: String = ""
            val lineasActualizadas = lineas.filter { habitacion: String ->
                val seleccionado: Boolean = habitacion.contains(codigoHabitacion.toString())
                if (seleccionado) {
                    lineaActualizar = habitacion
                }
                return@filter !seleccionado
            }.toList()

            val habitacion = lineaActualizar.split(", ").toList()
            val habitacionActualizada = habitacion.map { atributo: String ->
                when (habitacion.indexOf(atributo)) {
                    1 -> tipoHabitacion ?: atributo
                    2 -> precioNoche?.toString() ?: atributo
                    3 -> ocupada?.toString() ?: atributo
                    4 -> fechaUltimaOcupacion?.toString() ?: atributo
                    else -> atributo
                }
            }

            archivoHabitaciones.writeText(lineasActualizadas.joinToString("\n"))
            archivoHabitaciones.appendText("\n${habitacionActualizada.toString().trimStart('[').trimEnd(']')}")
            println("Habitación $codigoHabitacion ha sido actualizada")
        }
    }

    override fun toString(): String {
        return "Habitacion(codigoHabitacion=$codigoHabitacion, tipoHabitacion='$tipoHabitacion', precioNoche=$precioNoche, ocupada=$ocupada, fechaUltimaOcupacion=${fechaUltimaOcupacion ?: "null"})"
    }

}