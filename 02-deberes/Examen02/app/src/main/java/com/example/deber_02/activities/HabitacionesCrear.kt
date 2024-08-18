package com.example.deber_02.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R
import com.example.deber_02.base_datos.EBaseDeDatos
import com.example.deber_02.helpers.ESqliteHelperHabitacion
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeParseException

class HabitacionesCrear : AppCompatActivity() {

    private lateinit var tipoHabitacionEditText: EditText
    private lateinit var precioNocheEditText: EditText
    private lateinit var ocupadaCheckBox: CheckBox
    private lateinit var botonGuardar: Button
    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitaciones_crear)
        setupViews()
        setupInsets()

        val habitacionId = intent.getIntExtra("habitacion_id", -1)

        botonGuardar.setOnClickListener {
            handleSaveClick(habitacionId)
        }
    }

    private fun setupViews() {
        tipoHabitacionEditText = findViewById(R.id.edt_tipo_habitacion)
        precioNocheEditText = findViewById(R.id.edt_precio_noche)
        ocupadaCheckBox = findViewById(R.id.cb_ocupada)
        botonGuardar = findViewById(R.id.btn_guardar_habitacion)
        container = findViewById(R.id.cl_crear_habitacion)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleSaveClick(habitacionId: Int) {
        val tipoHabitacion = tipoHabitacionEditText.text.toString()
        val precioNocheStr = precioNocheEditText.text.toString()
        val ocupada = ocupadaCheckBox.isChecked

        if (tipoHabitacion.isNotEmpty() && precioNocheStr.isNotEmpty()) {
            val precioNoche = precioNocheStr.toDoubleOrNull()
            if (precioNoche != null) {
                val fechaUltimaOcupacion = obtenerFechaUltimaOcupacion()

                val respuesta = if (habitacionId == -1) {
                    crearNuevaHabitacion(tipoHabitacion, precioNoche, ocupada, fechaUltimaOcupacion)
                } else {
                    actualizarHabitacionExistente(habitacionId, tipoHabitacion, precioNoche, ocupada, fechaUltimaOcupacion)
                }

                if (respuesta) {
                    irActividad(Habitaciones::class.java)
                } else {
                    mostrarSnackBar("Error al guardar la Habitación.")
                }
            } else {
                mostrarSnackBar("Precio inválido.")
            }
        } else {
            mostrarSnackBar("Datos inválidos.")
        }
    }


    private fun obtenerFechaUltimaOcupacion(): LocalDate {
        return LocalDate.now()
    }


    private fun crearNuevaHabitacion(
        tipoHabitacion: String,
        precioNoche: Double,
        ocupada: Boolean,
        fechaUltimaOcupacion: LocalDate?
    ): Boolean {
        return EBaseDeDatos.tablaHabitacion?.crearHabitacion(tipoHabitacion, precioNoche, ocupada, fechaUltimaOcupacion) ?: false
    }

    private fun actualizarHabitacionExistente(
        habitacionId: Int,
        tipoHabitacion: String,
        precioNoche: Double,
        ocupada: Boolean,
        fechaUltimaOcupacion: LocalDate?
    ): Boolean {
        return EBaseDeDatos.tablaHabitacion?.actualizarHabitacion(habitacionId, tipoHabitacion, precioNoche, ocupada, fechaUltimaOcupacion) ?: false
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(container, texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
