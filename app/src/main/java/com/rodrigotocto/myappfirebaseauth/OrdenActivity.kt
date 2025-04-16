package com.rodrigotocto.myappfirebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigotocto.myappfirebaseauth.Models.OrdenDetalle
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityOrdenBinding


class OrdenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdenBinding
    private lateinit var myAdapter: MyAdapterCardOrdenDetalle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOrdenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener(View.OnClickListener {
            OpenInicio()

        })

        binding.recyclerViewCoffeeItems.layoutManager = LinearLayoutManager(this)
        getAllData()
    }
    private fun OpenInicio(){
        val intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)

    }

    private fun getAllData() {
        val productosCafe = CartManager.cartItems.map { detalle ->
            OrdenDetalle(
                detalle.ordenDetalleId, // o trae el nombre real desde otro lugar si lo tienes
                detalle.cantidad,
                detalle.subtotal,
                detalle.productoId,
                detalle.nombre,
                detalle.imageView
            )
        }

        myAdapter = MyAdapterCardOrdenDetalle(this, productosCafe)
        binding.recyclerViewCoffeeItems.adapter = myAdapter
    }

}