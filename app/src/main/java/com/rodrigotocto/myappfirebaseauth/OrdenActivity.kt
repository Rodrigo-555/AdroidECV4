package com.rodrigotocto.myappfirebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigotocto.myappfirebaseauth.Models.Orden
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityOrdenBinding


class OrdenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrdenBinding
    private lateinit var myAdapter: MyAdapterCardOrden

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
        val productosCafe = listOf(
            Orden("Espresso",3.50, 3,10.50, R.drawable.espresso),
            Orden("Cappuccino",3.50,5, 17.50,  R.drawable.capuccino),
            Orden("Latte",3.50, 2,7.00,  R.drawable.latte_art),
            Orden("Americano",3.50,5, 17.50,  R.drawable.cafe_americano),
            Orden("Moca",3.50,4, 14.00,  R.drawable.moca),
            Orden("Frappuccino",3.50,3, 10.50,  R.drawable.frapuchino)
        )

        myAdapter = MyAdapterCardOrden(this, productosCafe)
        binding.recyclerViewCoffeeItems.adapter = myAdapter
    }

}