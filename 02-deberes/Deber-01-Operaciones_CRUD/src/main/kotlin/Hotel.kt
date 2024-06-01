import java.io.File
import java.time.LocalDate
import kotlin.collections.ArrayList

class Hotel(
    val codigoHotel: Int,
    val nombreHotel: String,
    val categoria: Int, // Representa la categoría del hotel (por ejemplo, 1 estrella, 2 estrellas, etc.)
    val fechaInauguracion: LocalDate,
    val listaHabitaciones: ArrayList<Habitacion>
) {
    init {
        this.codigoHotel
        this.nombreHotel
        this.categoria
        this.fechaInauguracion
        this.listaHabitaciones
    }

    companion object {
        // Ruta del archivo para almacenar la información de los hoteles
        private val archivoHoteles: File = File("src/main/hoteles.txt")

        fun crearHotel(hotel: Hotel): Unit {
            println("\n---- CREAR HOTEL ----")
            val registro = "${hotel.codigoHotel}, ${hotel.nombreHotel}, ${hotel.categoria}, ${hotel.fechaInauguracion}, " +
                    "HABITACIONES--${hotel.listaHabitaciones}--"
            archivoHoteles.appendText("\n$registro")
            println("Hotel registrado con éxito! [$registro]")
        }

        fun verHoteles(): Unit {
            println("\n---- HOTELES REGISTRADOS ----")
            println(archivoHoteles.readText())
        }

        fun eliminarHotel(codigoHotel: Int): Unit {
            println("\n---- ELIMINAR HOTEL ----")
            val lineas = archivoHoteles.readLines()
            val lineasActualizadas = lineas.filter { !it.contains(codigoHotel.toString()) }
            archivoHoteles.writeText(lineasActualizadas.joinToString("\n"))
            println("Hotel $codigoHotel ha sido eliminado")
        }

        fun actualizarHotel(
            codigoHotel: Int,
            nombreHotel: String? = null,
            categoria: Int? = null,
            fechaInauguracion: LocalDate? = null,
            listaHabitaciones: ArrayList<Habitacion>? = null
        ): Unit {
            println("\n---- ACTUALIZAR HOTEL ----")
            val lineas = archivoHoteles.readLines()
            var lineaActualizar: String = ""
            val lineasActualizadas = lineas.filter { hotel: String ->
                val seleccionado: Boolean = hotel.contains(codigoHotel.toString())
                if (seleccionado) {
                    lineaActualizar = hotel
                }
                return@filter !seleccionado
            }.toList()

            val hotel = lineaActualizar.split(", ").toList()
            val hotelActualizado = hotel.map { atributo: String ->
                when (hotel.indexOf(atributo)) {
                    1 -> nombreHotel ?: atributo
                    2 -> categoria?.toString() ?: atributo
                    3 -> fechaInauguracion?.toString() ?: atributo
                    4 -> listaHabitaciones?.toString() ?: atributo
                    else -> atributo
                }
            }


            archivoHoteles.writeText(lineasActualizadas.joinToString("\n"))
            archivoHoteles.appendText("\n${hotelActualizado.toString().trimStart('[').trimEnd(']')}")
            println("Hotel $codigoHotel ha sido actualizado")
        }

    }

    override fun toString(): String {
        val habitacionesStr = listaHabitaciones.joinToString(separator = "\n") { habitacion ->
            habitacion.toString()
        }

        return "Hotel(codigoHotel=$codigoHotel, nombreHotel='$nombreHotel', categoria=$categoria, fechaInauguracion=$fechaInauguracion, " +
                "listaHabitaciones=[\n$habitacionesStr\n])"
    }

}