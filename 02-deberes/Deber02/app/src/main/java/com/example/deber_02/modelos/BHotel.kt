import android.os.Parcel
import android.os.Parcelable
import java.io.File
import java.time.LocalDate

class BHotel(
    val codigoHotel: Int,
    val nombreHotel: String,
    val categoria: Int,
    val fechaInauguracion: LocalDate,
    val listaHabitaciones: MutableList<BHabitacion> = mutableListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        LocalDate.parse(parcel.readString()),
        mutableListOf<BHabitacion>().apply {
            parcel.readList(this as List<*>, BHabitacion::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(codigoHotel)
        parcel.writeString(nombreHotel)
        parcel.writeInt(categoria)
        parcel.writeString(fechaInauguracion.toString())
        parcel.writeList(listaHabitaciones)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BHotel> {
        private val archivoHoteles: File = File("src/main/hoteles.txt")

        override fun createFromParcel(parcel: Parcel): BHotel {
            return BHotel(parcel)
        }

        override fun newArray(size: Int): Array<BHotel?> {
            return arrayOfNulls(size)
        }

        fun crearHotel(hotel: BHotel) {
            println("\n---- CREAR HOTEL ----")
            val registro = "${hotel.codigoHotel}, ${hotel.nombreHotel}, ${hotel.categoria}, ${hotel.fechaInauguracion}, HABITACIONES--${hotel.listaHabitaciones}--"
            archivoHoteles.appendText("\n$registro")
            println("Hotel registrado con éxito! [$registro]")
        }

        fun verHoteles() {
            println("\n---- HOTELES REGISTRADOS ----")
            println(archivoHoteles.readText())
        }

        fun eliminarHotel(codigoHotel: Int) {
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
            listaHabitaciones: MutableList<BHabitacion>? = null
        ) {
            println("\n---- ACTUALIZAR HOTEL ----")
            val lineas = archivoHoteles.readLines()
            var lineaActualizar: String = ""
            val lineasActualizadas = lineas.filter { hotel: String ->
                val seleccionado: Boolean = hotel.contains(codigoHotel.toString())
                if (seleccionado) {
                    lineaActualizar = hotel
                }
                return@filter !seleccionado
            }

            val hotel = lineaActualizar.split(", ")
            val hotelActualizado = hotel.mapIndexed { index, atributo ->
                when (index) {
                    1 -> nombreHotel ?: atributo
                    2 -> categoria?.toString() ?: atributo
                    3 -> fechaInauguracion?.toString() ?: atributo
                    4 -> listaHabitaciones?.toString() ?: atributo
                    else -> atributo
                }
            }

            archivoHoteles.writeText(lineasActualizadas.joinToString("\n"))
            archivoHoteles.appendText("\n${hotelActualizado.joinToString(", ")}")
            println("Hotel $codigoHotel ha sido actualizado")
        }
    }

    override fun toString(): String {
        val habitacionesStr = listaHabitaciones.joinToString(separator = "\n") { habitacion ->
            habitacion.toString()
        }
        return "Hotel(codigoHotel=$codigoHotel, nombreHotel='$nombreHotel', categoria=$categoria, fechaInauguracion=$fechaInauguracion, listaHabitaciones=[\n$habitacionesStr\n])"
    }
}
