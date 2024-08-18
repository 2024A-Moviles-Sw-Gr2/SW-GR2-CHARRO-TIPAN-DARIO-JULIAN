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

        override fun createFromParcel(parcel: Parcel): BHotel {
            return BHotel(parcel)
        }

        override fun newArray(size: Int): Array<BHotel?> {
            return arrayOfNulls(size)
        }

    }

    override fun toString(): String {
        val habitacionesStr = listaHabitaciones.joinToString(separator = "\n") { habitacion ->
            habitacion.toString()
        }
        return "Hotel(codigoHotel=$codigoHotel, nombreHotel='$nombreHotel', categoria=$categoria, fechaInauguracion=$fechaInauguracion, listaHabitaciones=[\n$habitacionesStr\n])"
    }
}
