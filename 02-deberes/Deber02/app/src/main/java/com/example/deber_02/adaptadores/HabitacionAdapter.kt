package com.example.deber_02.adaptadores

import BHabitacion
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class HabitacionAdapter(context: Context, habitaciones: List<BHabitacion>) : ArrayAdapter<BHabitacion>(context, 0, habitaciones) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val habitacion = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = habitacion?.tipoHabitacion ?: "Tipo desconocido"
        text2.text = "Precio por noche: $${habitacion?.precioNoche?.toString() ?: "Desconocido"} - ${if (habitacion?.ocupada == true) "Ocupada" else "Disponible"}"

        view.tag = habitacion?.codigoHabitacion

        return view
    }
}
