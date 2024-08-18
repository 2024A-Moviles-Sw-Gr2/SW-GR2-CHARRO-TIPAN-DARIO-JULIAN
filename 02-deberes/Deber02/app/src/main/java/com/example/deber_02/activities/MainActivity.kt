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
import com.example.deber_02.adaptadores.HotelAdapter
import com.example.deber_02.base_datos.EBaseDeDatos
import com.example.deber_02.helpers.ESqliteHelperHabitacion
import com.example.deber_02.helpers.ESqliteHelperHotel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adaptador: HotelAdapter
    private var posicionItemSeleccionado = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inicializarBd()
        initViews()
        setupListView()
        setupWindowInsets()
        setupButtons()
    }

    private fun inicializarBd() {
        // Inicializar base de datos
        EBaseDeDatos.tablaHotel = ESqliteHelperHotel(this)
        EBaseDeDatos.tablaHabitacion = ESqliteHelperHabitacion(this)
    }

    private fun initViews() {
        listView = findViewById(R.id.lv_hotel)
    }

    private fun setupListView() {
        adaptador = HotelAdapter(this, EBaseDeDatos.tablaHotel!!.obtenerTodosLosHoteles())
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupButtons() {
        val botonCrear = findViewById<Button>(R.id.btn_crear_hotel)


        botonCrear.setOnClickListener {
            irActividad(HotelesCrear::class.java, -1)
        }


    }

    private fun irActividad(clase: Class<*>, hotelId: Int? = null, ubicacion: String? = null) {
        val intent = Intent(this, clase).apply {
            // Agregar "hotel_id" solo si no es nulo
            hotelId?.let { putExtra("hotel_id", it) }

            // Agregar "ubicacion" solo si no es nulo ni vacío
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
        menuInflater.inflate(R.menu.menu_hotel, menu)
        posicionItemSeleccionado = (menuInfo as AdapterView.AdapterContextMenuInfo).position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val hotelId = info.targetView.tag as Int

        return when (item.itemId) {
            R.id.menu_hotel_editar -> {
                mostrarSnackbar("Editar $posicionItemSeleccionado")
                irActividad(HotelesCrear::class.java, hotelId)
                true
            }
            R.id.menu_hotel_eliminar -> {
                mostrarSnackbar("Eliminar $posicionItemSeleccionado")
                EBaseDeDatos.tablaHotel!!.eliminarHotel(hotelId)
                mostrarSnackbar("Hotel con id: $hotelId eliminado")
                irActividad(MainActivity::class.java, -1)
                true
            }
            R.id.menu_hotel_ver -> {
                irActividad(Habitaciones::class.java, hotelId)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        Snackbar.make(
            findViewById(R.id.main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        ).show()
    }
}
