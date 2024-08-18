package com.example.deber_02.base_datos

import android.content.Context
import com.example.deber_02.helpers.ESqliteHelperHabitacion
import com.example.deber_02.helpers.ESqliteHelperHotel


class EBaseDeDatos {
    companion object {
        var tablaHotel: ESqliteHelperHotel? = null
        var tablaHabitacion: ESqliteHelperHabitacion? = null


    }
}
