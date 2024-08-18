package com.example.deber_02.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R
import com.example.deber_02.helpers.ESqliteHelperHotel

import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HotelesCrear : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var categoriaEditText: EditText
    private lateinit var fechaAperturaEditText: EditText
    private lateinit var botonGuardar: Button
    private lateinit var container: FrameLayout

    private lateinit var sqliteHelper: ESqliteHelperHotel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hoteles_crear)
        setupViews()
        setupInsets()

        sqliteHelper = ESqliteHelperHotel(this)

        val hotelId = intent.getIntExtra("hotel_id", -1)

        botonGuardar.setOnClickListener {
            handleSaveClick(hotelId)
        }
    }

    private fun setupViews() {
        nombreEditText = findViewById(R.id.txt_nombre_hotel)
        categoriaEditText = findViewById(R.id.txt_categoria_hotel)
        fechaAperturaEditText = findViewById(R.id.txt_fecha_apertura)
        botonGuardar = findViewById(R.id.btn_guardar_hotel)
        container = findViewById(R.id.cl_crear_hotel)
    }

    private fun setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleSaveClick(hotelId: Int) {
        val nombre = nombreEditText.text.toString()
        val categoriaStr = categoriaEditText.text.toString()
        val fechaAperturaStr = fechaAperturaEditText.text.toString()

        val fechaApertura = parseFecha(fechaAperturaStr)
        val categoria = categoriaStr.toIntOrNull()

        if (validateInputs(nombre, categoria, fechaApertura)) {
            val respuesta = if (hotelId == -1) {
                crearNuevoHotel(nombre, categoria!!, fechaApertura!!)
            } else {
                actualizarHotelExistente(hotelId, nombre, categoria!!, fechaApertura!!)
            }

            if (respuesta) {
                irActividad(MainActivity::class.java)
            } else {
                mostrarSnackBar("Error al guardar el hotel.")
            }
        } else {
            mostrarSnackBar("Datos inválidos.\nNombre: $nombre, Categoría: $categoriaStr, Fecha: $fechaAperturaStr\n" +
                    "${nombre.isNotEmpty()} ${categoria != null} ${fechaApertura != null}")
        }
    }

    private fun parseFecha(fechaStr: String): LocalDate? {
        return try {
            LocalDate.parse(fechaStr, DateTimeFormatter.ISO_DATE)
        } catch (e: Exception) {
            null
        }
    }

    private fun validateInputs(nombre: String, categoria: Int?, fecha: LocalDate?): Boolean {
        return nombre.isNotEmpty() && categoria != null && fecha != null
    }

    private fun crearNuevoHotel(nombre: String, categoria: Int, fechaApertura: LocalDate): Boolean {
        return sqliteHelper.crearHotel(nombre, categoria, fechaApertura)
    }

    private fun actualizarHotelExistente(hotelId: Int, nombre: String, categoria: Int, fechaApertura: LocalDate): Boolean {
        return sqliteHelper.actualizarHotel(hotelId, nombre, categoria, fechaApertura)
    }

    private fun irActividad(clase: Class<*>) {
        startActivity(Intent(this, clase))
    }

    private fun mostrarSnackBar(texto: String) {
        Snackbar.make(container, texto, Snackbar.LENGTH_INDEFINITE).show()
    }
}
