import java.time.LocalDate

fun main() {
    // HABITACIONES
    // Crear habitaciones
    Habitacion.crearHabitacion(
        Habitacion(
            codigoHabitacion = 101,
            tipoHabitacion = "Sencilla",
            precioNoche = 50.0,
            ocupada = false,
            fechaUltimaOcupacion = null
        )
    )
    Habitacion.crearHabitacion(
        Habitacion(
            codigoHabitacion = 102,
            tipoHabitacion = "Doble",
            precioNoche = 75.0,
            ocupada = true,
            fechaUltimaOcupacion = LocalDate.of(2024, 5, 30)
        )
    )

    // Ver habitaciones
    Habitacion.verHabitaciones()

    // Actualizar habitación
    Habitacion.actualizarHabitacion(
        codigoHabitacion = 102,
        precioNoche = 80.0
    )

    // Verificar
    Habitacion.verHabitaciones()

    // Eliminar habitación
    Habitacion.eliminarHabitacion(101)

    // Verificar
    Habitacion.verHabitaciones()

    // HOTELES
    // Crear hoteles
    val listaHabitaciones = arrayListOf(
        Habitacion(
            codigoHabitacion = 201,
            tipoHabitacion = "Suite",
            precioNoche = 120.0,
            ocupada = false,
            fechaUltimaOcupacion = null
        ),
        Habitacion(
            codigoHabitacion = 202,
            tipoHabitacion = "Doble",
            precioNoche = 80.0,
            ocupada = true,
            fechaUltimaOcupacion = LocalDate.of(2024, 5, 30)
        )
    )

    Hotel.crearHotel(
        Hotel(
            codigoHotel = 1002,
            nombreHotel = "Hotel Luxury",
            categoria = 5,
            fechaInauguracion = LocalDate.of(2024, 5, 30),
            listaHabitaciones = listaHabitaciones
        )
    )

    // Ver hoteles
    Hotel.verHoteles()

    // Actualizar hotel
    Hotel.actualizarHotel(
        codigoHotel = 1001,
        categoria = 4
    )

    // Verificar
    Hotel.verHoteles()

    // Eliminar hotel
    //Hotel.eliminarHotel(1001)

    // Verificar
    Hotel.verHoteles()
}