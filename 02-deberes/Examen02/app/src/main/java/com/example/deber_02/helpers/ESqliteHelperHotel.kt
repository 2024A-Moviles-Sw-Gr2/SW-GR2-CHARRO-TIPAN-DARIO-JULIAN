package com.example.deber_02.helpers

import BHotel
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ESqliteHelperHotel(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "hoteles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaHotel = """
            CREATE TABLE HOTEL(
                codigoHotel INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreHotel VARCHAR(100),
                categoria INTEGER,
                fechaInauguracion VARCHAR(50)
            )
        """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaHotel)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        // Aquí puedes manejar la actualización de la base de datos si es necesario
    }

    fun crearHotel(
        nombreHotel: String,
        categoria: Int,
        fechaInauguracion: LocalDate
    ): Boolean {
        val db = writableDatabase
        val valoresHotel = ContentValues()
        valoresHotel.put("nombreHotel", nombreHotel)
        valoresHotel.put("categoria", categoria)
        valoresHotel.put("fechaInauguracion", fechaInauguracion.format(DateTimeFormatter.ISO_DATE))

        val resultado = db.insert("HOTEL", null, valoresHotel)
        db.close()
        return resultado != -1L
    }

    fun eliminarHotel(codigoHotel: Int): Boolean {
        val db = writableDatabase
        val parametros = arrayOf(codigoHotel.toString())
        val resultadoEliminar = db.delete("HOTEL", "codigoHotel=?", parametros)
        db.close()
        return resultadoEliminar != -1
    }

    fun actualizarHotel(
        codigoHotel: Int,
        nombreHotel: String,
        categoria: Int,
        fechaInauguracion: LocalDate
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("nombreHotel", nombreHotel)
        valores.put("categoria", categoria)
        valores.put("fechaInauguracion", fechaInauguracion.format(DateTimeFormatter.ISO_DATE))

        val parametros = arrayOf(codigoHotel.toString())
        val resultado = db.update("HOTEL", valores, "codigoHotel=?", parametros)
        db.close()
        return resultado != -1
    }

    fun consultarHotelPorID(codigoHotel: Int): BHotel? {
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM HOTEL WHERE codigoHotel = ?"
        val parametrosConsulta = arrayOf(codigoHotel.toString())
        val resultadoConsulta = db.rawQuery(scriptConsulta, parametrosConsulta)

        return if (resultadoConsulta.moveToFirst()) {
            val hotel = BHotel(
                resultadoConsulta.getInt(0),  // codigoHotel
                resultadoConsulta.getString(1),  // nombreHotel
                resultadoConsulta.getInt(2),  // categoria
                LocalDate.parse(resultadoConsulta.getString(3))  // fechaInauguracion
            )
            resultadoConsulta.close()
            db.close()
            hotel
        } else {
            resultadoConsulta.close()
            db.close()
            null
        }
    }

    fun obtenerTodosLosHoteles(): List<BHotel> {
        val listaHoteles = mutableListOf<BHotel>()
        val db = readableDatabase
        val scriptConsulta = "SELECT * FROM HOTEL"
        val resultadoConsulta = db.rawQuery(scriptConsulta, null)

        if (resultadoConsulta.moveToFirst()) {
            do {
                val hotel = BHotel(
                    resultadoConsulta.getInt(0),  // codigoHotel
                    resultadoConsulta.getString(1),  // nombreHotel
                    resultadoConsulta.getInt(2),  // categoria
                    LocalDate.parse(resultadoConsulta.getString(3))  // fechaInauguracion
                )
                listaHoteles.add(hotel)
            } while (resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        db.close()
        return listaHoteles
    }
}
