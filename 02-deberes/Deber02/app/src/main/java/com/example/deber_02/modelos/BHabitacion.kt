import android.os.Parcel
import android.os.Parcelable
import java.io.File
import java.time.LocalDate

class BHabitacion(
    val codigoHabitacion: Int,
    val tipoHabitacion: String,
    val precioNoche: Double,
    val ocupada: Boolean,
    val fechaUltimaOcupacion: LocalDate? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()?.let { LocalDate.parse(it) }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(codigoHabitacion)
        parcel.writeString(tipoHabitacion)
        parcel.writeDouble(precioNoche)
        parcel.writeByte(if (ocupada) 1 else 0)
        parcel.writeString(fechaUltimaOcupacion?.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BHabitacion> {
        private val archivoHabitaciones: File = File("src/main/habitaciones.txt")

        override fun createFromParcel(parcel: Parcel): BHabitacion {
            return BHabitacion(parcel)
        }

        override fun newArray(size: Int): Array<BHabitacion?> {
            return arrayOfNulls(size)
        }

        fun crearHabitacion(habitacion: BHabitacion) {
            println("\n---- CREACION DE HABITACION ----")
            val registro = "${habitacion.codigoHabitacion}, ${habitacion.tipoHabitacion}, ${habitacion.precioNoche}, ${habitacion.ocupada}, ${habitacion.fechaUltimaOcupacion ?: "N/A"}"
            archivoHabitaciones.appendText("\n$registro")
            println("Habitación registrada con éxito! [$registro]")
        }

        fun verHabitaciones() {
            println("\n---- HABITACIONES REGISTRADAS ----")
            println(archivoHabitaciones.readText())
        }

        fun eliminarHabitacion(codigoHabitacion: Int) {
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
        ) {
            println("\n---- ACTUALIZAR HABITACION ----")
            val lineas = archivoHabitaciones.readLines()
            var lineaActualizar: String = ""
            val lineasActualizadas = lineas.filter { habitacion: String ->
                val seleccionado: Boolean = habitacion.contains(codigoHabitacion.toString())
                if (seleccionado) {
                    lineaActualizar = habitacion
                }
                return@filter !seleccionado
            }

            val habitacion = lineaActualizar.split(", ")
            val habitacionActualizada = habitacion.mapIndexed { index, atributo ->
                when (index) {
                    1 -> tipoHabitacion ?: atributo
                    2 -> precioNoche?.toString() ?: atributo
                    3 -> ocupada?.toString() ?: atributo
                    4 -> fechaUltimaOcupacion?.toString() ?: atributo
                    else -> atributo
                }
            }

            archivoHabitaciones.writeText(lineasActualizadas.joinToString("\n"))
            archivoHabitaciones.appendText("\n${habitacionActualizada.joinToString(", ")}")
            println("Habitación $codigoHabitacion ha sido actualizada")
        }
    }

    override fun toString(): String {
        return "Habitacion(codigoHabitacion=$codigoHabitacion, tipoHabitacion='$tipoHabitacion', precioNoche=$precioNoche, ocupada=$ocupada, fechaUltimaOcupacion=${fechaUltimaOcupacion ?: "null"})"
    }
}
