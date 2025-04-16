package com.rodrigotocto.myappfirebaseauth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var nGoogleMap: GoogleMap
    private lateinit var searchBar: CardView
    private lateinit var locationButton: View
    private lateinit var addAddressButton: Button
    private lateinit var tipoViviendaEditText: EditText
    private lateinit var pisoEditText: EditText
    private lateinit var referenciasEditText: EditText
    private lateinit var direccionTextView: TextView

    // Current location coordinates
    private val currentLocation = LatLng(-12.0464, -77.0428) // Default: Mall del Sur coordinates

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maps)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements
        initializeUIElements()

        // Set up click listeners
        setupClickListeners()

        // Get the SupportMapFragment and notify when it's ready to use
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapfragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initializeUIElements() {
        searchBar = findViewById(R.id.searchBarCard)

        // Find the location button inside the direction input field
        val directionInputLayout = findViewById<View>(R.id.bottomSheet)
            .findViewById<LinearLayout>(R.id.directionInputLayout)
        locationButton = directionInputLayout.findViewById<View>(R.id.locationButton)

        // Find form fields
        addAddressButton = findViewById<View>(R.id.bottomSheet)
            .findViewById(R.id.addAddressButton)
        tipoViviendaEditText = findViewById<View>(R.id.bottomSheet)
            .findViewById(R.id.tipoViviendaEditText)
        pisoEditText = findViewById<View>(R.id.bottomSheet)
            .findViewById(R.id.pisoEditText)
        referenciasEditText = findViewById<View>(R.id.bottomSheet)
            .findViewById(R.id.referenciasEditText)
        direccionTextView = findViewById<View>(R.id.bottomSheet)
            .findViewById(R.id.direccionTextView)
    }

    private fun setupClickListeners() {
        // Search bar click listener
        searchBar.setOnClickListener {
            Toast.makeText(this, "Búsqueda de dirección iniciada", Toast.LENGTH_SHORT).show()
            // Here you would typically show a search interface or dialog
        }

        // Location button click listener
        locationButton.setOnClickListener {
            Toast.makeText(this, "Usando ubicación actual", Toast.LENGTH_SHORT).show()
            // In a real app, you would request location permissions and get the user's location
            // For now, we'll just zoom to the current marker
            nGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18f))
        }

        // Add address button click listener
        addAddressButton.setOnClickListener {
            val tipoVivienda = tipoViviendaEditText.text.toString()
            val piso = pisoEditText.text.toString()
            val referencias = referenciasEditText.text.toString()
            val direccion = direccionTextView.text.toString()

            // Validate form data
            if (tipoVivienda.isEmpty() || piso.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Success message
            val message = "Dirección guardada: $direccion, $tipoVivienda, Piso $piso"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            // Here you would typically save the address to a database
            // For now, we'll just finish the activity
            // finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        nGoogleMap = googleMap

        // Set Mall del Sur location as default
        nGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))

        // Add custom marker
        val markerOptions = MarkerOptions()
            .position(currentLocation)
            .title("Mall del Sur")
            .snippet("San Juan de Miraflores")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        nGoogleMap.addMarker(markerOptions)

        // Set up map click listener to allow selecting a new location
        nGoogleMap.setOnMapClickListener { latLng ->
            // Clear existing markers
            nGoogleMap.clear()

            // Add a new marker at the clicked location
            val newMarkerOptions = MarkerOptions()
                .position(latLng)
                .title("Ubicación seleccionada")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            nGoogleMap.addMarker(newMarkerOptions)

            // Update the current location
            // In a real app, you would reverse geocode to get the address
            Toast.makeText(this, "Nueva ubicación seleccionada", Toast.LENGTH_SHORT).show()
        }
    }
}