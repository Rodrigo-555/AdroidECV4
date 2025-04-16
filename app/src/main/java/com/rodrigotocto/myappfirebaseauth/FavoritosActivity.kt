package com.rodrigotocto.myappfirebaseauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityFavoritosBinding

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var myAdapter: MyAdapterCardFavourites

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.navigation_favorites


        binding.carritoCompras.setOnClickListener(View.OnClickListener {
            OpenOrdenes()
        })


        binding.productFavoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        getAllData()

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> openActivity(InicioActivity::class.java)
                R.id.navigation_favorites -> openActivity(FavoritosActivity::class.java)
                R.id.navigation_profile -> openActivity(PerfilActivity::class.java)
                else ->{

                }
            }
            true
        }


    }
    private fun OpenOrdenes(){
        val intent = Intent(this, OrdenActivity::class.java)
        startActivity(intent)

    }
    private fun openActivity(activityClass: Class<out Activity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun getAllData() {
////        val productosCafe = listOf(
////            Producto("Espresso", "Café fuerte y concentrado servido en una taza pequeña", 8.0, R.drawable.espresso),
////            Producto("Cappuccino", "Espresso con leche vaporizada y espuma de leche", 10.0, R.drawable.capuccino),
////            Producto("Latte", "Espresso con abundante leche vaporizada y un toque de espuma", 10.5, R.drawable.latte_art),
////            Producto("Americano", "Espresso diluido con agua caliente, más suave que el espresso solo", 7.5, R.drawable.cafe_americano),
////            Producto("Moca", "Latte con chocolate y crema batida", 11.0, R.drawable.moca),
////            Producto("Frappuccino", "Bebida fría a base de café, hielo, leche y sabores dulces", 12.0, R.drawable.frapuchino)
////        )
//
//        myAdapter = MyAdapterCardFavourites(this, productosCafe)
//        binding.productFavoritesRecyclerView.adapter = myAdapter
    }

}