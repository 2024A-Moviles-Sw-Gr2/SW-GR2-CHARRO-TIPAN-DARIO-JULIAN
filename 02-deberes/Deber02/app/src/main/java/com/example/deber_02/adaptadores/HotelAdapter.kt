package com.example.deber_02.adaptadores

import BHotel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class HotelAdapter(context: Context, hoteles: List<BHotel>) : ArrayAdapter<BHotel>(context, 0, hoteles) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val hotel = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)

        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = hotel?.nombreHotel ?: "Nombre desconocido"
        text2.text = "Categoría: ${hotel?.categoria ?: "Desconocida"} - Fecha inauguración: ${hotel?.fechaInauguracion ?: "Desconocida"}"

        view.tag = hotel?.codigoHotel

        return view
    }
}
