package com.example.deber_02.mapa
class Ubicacion (
     val nombre: String,
     val latitud: Double,
     val longitud: Double
) {

    companion object {

        val listUbicaciones =  arrayListOf<Ubicacion>()

        fun getUbicaciones(): ArrayList<Ubicacion>{
            listUbicaciones.add(Ubicacion("Ubicacion1", -0.258476, -78.4840967))

            return listUbicaciones
        }
    }
}