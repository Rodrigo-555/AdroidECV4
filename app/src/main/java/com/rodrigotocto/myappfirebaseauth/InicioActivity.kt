package com.rodrigotocto.myappfirebaseauth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrehuamani.proyectofinal.MyAdapter
import com.andrehuamani.proyectofinal.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityInicioBinding

class InicioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        getAllData()
    }
    private fun getAllData() {
        val productosCafe = listOf(
            Producto("Espresso", "Café fuerte y concentrado servido en una taza pequeña", 8.0, R.drawable.espresso),
            Producto("Cappuccino", "Espresso con leche vaporizada y espuma de leche", 10.0, R.drawable.capuccino),
            Producto("Latte", "Espresso con abundante leche vaporizada y un toque de espuma", 10.5, R.drawable.latte_art),
            Producto("Americano", "Espresso diluido con agua caliente, más suave que el espresso solo", 7.5, R.drawable.cafe_americano),
            Producto("Moca", "Latte con chocolate y crema batida", 11.0, R.drawable.moca),
            Producto("Frappuccino", "Bebida fría a base de café, hielo, leche y sabores dulces", 12.0, R.drawable.frapuchino)
        )

        myAdapter = MyAdapter(this, productosCafe)
        binding.productRecyclerView.adapter = myAdapter
    }

}