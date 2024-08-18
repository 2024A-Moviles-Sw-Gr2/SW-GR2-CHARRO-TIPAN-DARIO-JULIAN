package com.example.deber_02.activities

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R
import com.example.deber_02.adaptadores.HabitacionAdapter
import com.example.deber_02.base_datos.EBaseDeDatos
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.Map

class Habitaciones : AppCompatActivity() {
    private var posicionItemSeleccionado = -1
    private var hotelId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitaciones)
        setupWindowInsets()

        hotelId = intent.getIntExtra("hotel_id", -1)
        setupListView()
        setupButtons()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.fl_habitaciones)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupListView() {
        val listView = findViewById<ListView>(R.id.lv_listHabitaciones)
        val adaptador =
            HabitacionAdapter(this, EBaseDeDatos.tablaHabitacion!!.obtenerTodasLasHabitaciones())
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() // Notificar las actualizaciones a la interfaz
        registerForContextMenu(listView)
    }

    private fun setupButtons() {
        val botonCrear = findViewById<Button>(R.id.btn_crear_habitacion)
        botonCrear.setOnClickListener {
            irActividad(HabitacionesCrear::class.java, hotelId, null)
        }

        val botonRegresar = findViewById<Button>(R.id.btn_regresar)
        botonRegresar.setOnClickListener {
            irActividad(MainActivity::class.java, -1, null)
        }

        val btnUbicacion1: Button = findViewById(R.id.btn_ubicacion1)

        // Configurar OnClickListeners
        btnUbicacion1.setOnClickListener {
            // Llamada a la función irActividad con todos los parámetros requeridos
            irActividad(com.example.deber_02.activities.Map::class.java, hotelId = 1, ubicacion = "Ubicacion1")
        }
    }

    // Función irActividad actualizada
    private fun irActividad(clase: Class<*>, hotelId: Int, habitacionId: Int?=null, ubicacion: String? = null) {
        val intent = Intent(this, clase).apply {
            putExtra("hotel_id", hotelId)
            habitacionId?.let {
                putExtra("habitacion_id", it)
            }
            ubicacion?.takeIf { it.isNotBlank() }?.let { putExtra("ubicacion", it) }
        }
        startActivity(intent)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_habitacion, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val habitacionId = info.targetView.tag as Int

        return when (item.itemId) {
            R.id.menu_habitacion_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                irActividad(HabitacionesCrear::class.java, hotelId, habitacionId)
                true
            }

            R.id.menu_habitacion_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                eliminarHabitacion(habitacionId)
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }

    private fun eliminarHabitacion(habitacionId: Int?) {
        if (habitacionId != null) {
            EBaseDeDatos.tablaHabitacion!!.eliminarHabitacion(habitacionId)
            mostrarSnackbar("Habitación con id: $habitacionId eliminada")
        }
        irActividad(Habitaciones::class.java, hotelId, null)
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(findViewById(R.id.fl_habitaciones), texto, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}
