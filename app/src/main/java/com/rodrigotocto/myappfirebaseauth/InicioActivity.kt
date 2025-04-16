package com.rodrigotocto.myappfirebaseauth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.rodrigotocto.myappfirebaseauth.DAO.ProductoDAO
import com.rodrigotocto.myappfirebaseauth.DAO.UsuarioDAO
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityInicioBinding

class InicioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private lateinit var myAdapter: MyAdapterCardProduct
    private lateinit var productoDao: ProductoDAO
    private lateinit var usuarioDAO: UsuarioDAO
    private var allProducts: List<Producto> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Inicializar el DAO para productos
        productoDao = ProductoDAO(this)
        productoDao.open()

        usuarioDAO = UsuarioDAO(this)
        usuarioDAO.open()

        binding.editLocation.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        })

        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)

        // Cargar todos los productos
        getAllData()


        binding.carritoCompras.setOnClickListener(View.OnClickListener {
            OpenOrdenes()

        })

        binding.editLocation.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        })

    }

    private fun openActivity(activityClass: Class<out Activity>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private fun OpenOrdenes(){
        val intent = Intent(this, OrdenActivity::class.java)
        startActivity(intent)

    }

    private fun getAllData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val email = user.email.toString()
            val usuariologeado = usuarioDAO.getUserByEmail(email)

            usuariologeado?.let { usuario ->
                val userId = usuario.id // Obtén el ID del usuario logueado

                // Cargar todos los productos de la BD
                allProducts = productoDao.getAllProducts()

                // Inicializar el adaptador con todos los productos y el userId
                myAdapter = MyAdapterCardProduct(this, allProducts, userId)
                binding.productRecyclerView.adapter = myAdapter
                setupFilterButtons()
            } ?: run {
                // Manejar el caso en que no se encuentra el usuario
                // Por ejemplo, mostrar un mensaje o redirigir a otra actividad
            }
        } ?: run {
            // Manejar el caso en que no hay un usuario logueado
            // Por ejemplo, redirigir a la pantalla de inicio de sesión
        }
    }

    private fun setupFilterButtons() {
        // Botón para mostrar todos los productos
        binding.chipAll.setOnClickListener {
            filterProductsByType(null)
        }

        // Botón para mostrar café
        binding.chipCafes.setOnClickListener {
            filterProductsByType("Cafe")
        }

        // Botón para mostrar bebidas frías
        binding.chipSandwiches.setOnClickListener {
            filterProductsByType("Sandwich")
        }

        // Botón para mostrar postres
        binding.chipPostres.setOnClickListener {
            filterProductsByType("Postre")
        }
    }

    private fun filterProductsByType(tipo: String?) {
        if (tipo == null) {
            // Si el tipo es null, mostrar todos los productos
            myAdapter.updateList(allProducts)
        } else {
            // Filtrar productos por tipo directamente desde la base de datos
            val filteredProducts = productoDao.getProductsByType(tipo)
            myAdapter.updateList(filteredProducts)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cerrar la conexión a la base de datos cuando la actividad se destruye
        productoDao.close()
    }

}