package com.example.deber_02.activities

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.deber_02.R
import com.example.deber_02.mapa.Ubicacion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
class Map : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ubicacion = intent.getStringExtra("ubicacion")
        solicitarPermisos()
        iniciarLogicaMapa(ubicacion!!)
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos=true
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    fun iniciarLogicaMapa(ubicacion: String){
        val fragmentoMapa =  supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        val info_ubicacion = Ubicacion.getUbicaciones().find { it.nombre == ubicacion }
        val coordenadas = LatLng(info_ubicacion!!.latitud, info_ubicacion.longitud)
        fragmentoMapa.getMapAsync{googleMap ->
            with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverCamaraConZoom(coordenadas)
                anadirMarcador(coordenadas, info_ubicacion.nombre )
            }

        }
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext

        with(mapa){
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }
    fun moverCamaraConZoom(latLang: LatLng, zoom: Float=17f){
        mapa.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLang, zoom
            )
        )
    }

    fun anadirMarcador(laLang: LatLng, tittle: String): Marker {
        return mapa.addMarker(
            MarkerOptions().position(laLang)
                .title(tittle)
        )!!
    }



}