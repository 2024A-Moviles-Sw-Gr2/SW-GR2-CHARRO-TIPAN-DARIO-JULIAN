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

        override fun createFromParcel(parcel: Parcel): BHabitacion {
            return BHabitacion(parcel)
        }

        override fun newArray(size: Int): Array<BHabitacion?> {
            return arrayOfNulls(size)
        }

    }

    override fun toString(): String {
        return "Habitacion(codigoHabitacion=$codigoHabitacion, tipoHabitacion='$tipoHabitacion', precioNoche=$precioNoche, ocupada=$ocupada, fechaUltimaOcupacion=${fechaUltimaOcupacion ?: "null"})"
    }
}
