package com.example.deber_02.helpers

import BHabitacion
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ESqliteHelperHabitacion(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "habitaciones",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaHabitacion = """
            CREATE TABLE HABITACION(
                codigoHabitacion INTEGER PRIMARY KEY AUTOINCREMENT,
                tipoHabitacion VARCHAR(50),
                precioNoche DOUBLE,
                ocupada INTEGER,
                fechaUltimaOcupacion VARCHAR(50)
            )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaHabitacion)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Aquí puedes manejar la actualización de la base de datos si es necesario
    }

    fun crearHabitacion(
        tipoHabitacion: String,
        precioNoche: Double,
        ocupada: Boolean,
        fechaUltimaOcupacion: LocalDate?
    ): Boolean {
        val db = writableDatabase
        val valoresHabitacion = ContentValues()
        valoresHabitacion.put("tipoHabitacion", tipoHabitacion)
        valoresHabitacion.put("precioNoche", precioNoche)
        valoresHabitacion.put("ocupada", if (ocupada) 1 else 0)
        valoresHabitacion.put("fechaUltimaOcupacion", fechaUltimaOcupacion?.format(DateTimeFormatter.ISO_DATE))

        val resultado = db.insert("HABITACION", null, valoresHabitacion)
        db.close()
        return resultado != -1L
    }

    fun eliminarHabitacion(codigoHabitacion: Int): Boolean {
        val db = writableDatabase
        val parametros = arrayOf(codigoHabitacion.toString())
        val resultadoEliminar = db.delete("HABITACION", "codigoHabitacion=?", parametros)
        db.close()
        return resultadoEliminar != -1
    }

    fun actualizarHabitacion(
        codigoHabitacion: Int,
        tipoHabitacion: String,
        precioNoche: Double,
        ocupada: Boolean,
        fechaUltimaOcupacion: LocalDate?
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("tipoHabitacion", tipoHabitacion)
        valores.put("precioNoche", precioNoche)
        valores.put("ocupada", if (ocupada) 1 else 0)
        valores.put("fechaUltimaOcupacion", fechaUltimaOcupacion?.format(DateTimeFormatter.ISO_DATE))

        val parametros = arrayOf(codigoHabitacion.toString())
        val resultado = db.update("HABITACION", valores, "codigoHabitacion=?", parametros)
        db.close()
        return resultado != -1
    }

    fun consultarHabitacionPorID(codigoHabitacion: Int): BHabitacion? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM HABITACION WHERE codigoHabitacion = ?"
        val parametrosConsulta = arrayOf(codigoHabitacion.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val habitacion = BHabitacion(
                resultadoConsulta.getInt(0),  // codigoHabitacion
                resultadoConsulta.getString(1),  // tipoHabitacion
                resultadoConsulta.getDouble(2),  // precioNoche
                resultadoConsulta.getInt(3) == 1,  // ocupada
                resultadoConsulta.getString(4)?.let { LocalDate.parse(it) }  // fechaUltimaOcupacion
            )
            resultadoConsulta.close()
            db.close()
            habitacion
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

    fun obtenerTodasLasHabitaciones(): List<BHabitacion> {
        val listaHabitaciones = mutableListOf<BHabitacion>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM HABITACION"
        val resultadoConsulta = db.rawQuery(scriptConsulta, null)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val habitacion = BHabitacion(
                    resultadoConsulta.getInt(0),  // codigoHabitacion
                    resultadoConsulta.getString(1),  // tipoHabitacion
                    resultadoConsulta.getDouble(2),  // precioNoche
                    resultadoConsulta.getInt(3) == 1,  // ocupada
                    resultadoConsulta.getString(4)?.let { LocalDate.parse(it) }  // fechaUltimaOcupacion
                )
                listaHabitaciones.add(habitacion)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaHabitaciones
    }
}
