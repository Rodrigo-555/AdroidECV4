package com.rodrigotocto.myappfirebaseauth


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var direccionTextView: TextView
    private lateinit var referenciasEditText: EditText
    private lateinit var tipoViviendaEditText: EditText
    private lateinit var pisoEditText: EditText
    private lateinit var addAddressButton: Button
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Inicializar vistas
        direccionTextView = findViewById(R.id.direccionTextView)
        referenciasEditText = findViewById(R.id.referenciasEditText)
        tipoViviendaEditText = findViewById(R.id.tipoViviendaEditText)
        pisoEditText = findViewById(R.id.pisoEditText)
        addAddressButton = findViewById(R.id.addAddressButton)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        // Ubicar usuario actual
        findViewById<ImageView>(R.id.locationButton).setOnClickListener {
            getCurrentLocation()
        }

        // Botón añadir dirección
        addAddressButton.setOnClickListener {
            val direccion = direccionTextView.text.toString()
            val referencias = referenciasEditText.text.toString()
            val tipoVivienda = tipoViviendaEditText.text.toString()
            val piso = pisoEditText.text.toString()

            Toast.makeText(this, "Dirección añadida:\n$direccion\n$referencias\n$tipoVivienda\nPiso: $piso", Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }


    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
                direccionTextView.text = "Ubicación actual: ${latLng.latitude}, ${latLng.longitude}"
            } else {
                Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }

}
