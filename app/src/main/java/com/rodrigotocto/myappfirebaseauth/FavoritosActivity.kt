package com.rodrigotocto.myappfirebaseauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.rodrigotocto.myappfirebaseauth.DAO.FavoritosDAO
import com.rodrigotocto.myappfirebaseauth.DAO.UsuarioDAO
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityFavoritosBinding

class FavoritosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var myAdapter: MyAdapterCardFavourites
    private lateinit var favoritosDAO: FavoritosDAO
    private lateinit var usuarioDAO: UsuarioDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.navigation_favorites


        binding.carritoCompras.setOnClickListener(View.OnClickListener {
            OpenOrdenes()
        })

        usuarioDAO = UsuarioDAO(this)
        usuarioDAO.open()

        favoritosDAO = FavoritosDAO(this)
        favoritosDAO.open()

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
    override fun onDestroy() {
        super.onDestroy()
        // Cerrar la base de datos al destruir la actividad
        favoritosDAO.close()
        usuarioDAO.close()
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
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val email = user.email.toString()
            val usuariologeado = usuarioDAO.getUserByEmail(email)

            // Ahora usamos usuariologeado dentro del mismo bloque
            usuariologeado?.let { usuario ->
                val userId = usuario.id
                val productosfav = favoritosDAO.getUserFavoriteProducts(usuario.id)
                myAdapter = MyAdapterCardFavourites(this, productosfav, userId)
                binding.productFavoritesRecyclerView.adapter = myAdapter
            } ?: run {
                // Manejar el caso en que no se encuentra el usuario
                // Por ejemplo, mostrar un mensaje o redirigir a otra actividad

            }

        }
    }


}