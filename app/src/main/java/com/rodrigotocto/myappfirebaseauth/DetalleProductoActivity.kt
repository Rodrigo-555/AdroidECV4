package com.rodrigotocto.myappfirebaseauth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.rodrigotocto.myappfirebaseauth.DAO.ProductoDAO
import com.rodrigotocto.myappfirebaseauth.Models.OrdenDetalle
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityDetalleProductoBinding
import com.rodrigotocto.myappfirebaseauth.databinding.ActivityInicioBinding

class DetalleProductoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleProductoBinding
    private lateinit var productoDao: ProductoDAO
    private lateinit var product: Producto
    private lateinit var producto_id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        productoDao = ProductoDAO(this)
        productoDao.open()
        binding.backButton.setOnClickListener(View.OnClickListener {
            OpenInicio()

        })

        producto_id = intent.getStringExtra("productoId") ?: ""
        binding.coffeeTitle.text = producto_id.toString()
        getData()
        setData()
        var precio_total = product.precio

        binding.caramelAdd.setOnClickListener {
            val count = binding.caramelCount.text.toString().toInt()
            binding.caramelCount.text = (count + 1).toString()
            precio_total += 1
            binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"

        }

        binding.caramelMinus.setOnClickListener {
            val count = binding.caramelCount.text.toString().toInt()
            if (count > 0) {
                binding.caramelCount.text = (count - 1).toString()
                precio_total -= 1
                binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"

            }
        }

        binding.bananaAdd.setOnClickListener {
            val count = binding.bananaCount.text.toString().toInt()
            binding.bananaCount.text = (count + 1).toString()
            precio_total += 1

        }

        binding.bananaMinus.setOnClickListener {
            val count = binding.bananaCount.text.toString().toInt()
            if (count > 0) {
                binding.bananaCount.text = (count - 1).toString()
                precio_total -= 1
                binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"

            }
        }

        binding.chocolateAdd.setOnClickListener {
            val count = binding.chocolateCount.text.toString().toInt()
            binding.chocolateCount.text = (count + 1).toString()
            precio_total += 1
            binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"

        }

        binding.chocolateMinus.setOnClickListener {
            val count = binding.chocolateCount.text.toString().toInt()
            if (count > 0) {
                binding.chocolateCount.text = (count - 1).toString()
                precio_total -= 1
                binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"

            }
        }

        binding.strawberryAdd.setOnClickListener {
            val count = binding.strawberryCount.text.toString().toInt()
            binding.strawberryCount.text = (count + 1).toString()
            precio_total += 1
            binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"
        }

        binding.strawberryMinus.setOnClickListener {
            val count = binding.strawberryCount.text.toString().toInt()
            if (count > 0) {
                binding.strawberryCount.text = (count - 1).toString()
                precio_total -= 1
                binding.addToCartButton.text = "Agregar al carrito | S/${precio_total}"
            }
        }

        binding.addToCartButton.setOnClickListener {
            val item = OrdenDetalle(-1, 1, precio_total, product.id,product.nombreProducto, product.tipo)
            CartManager.addItem(item)
            Toast.makeText(this, "Agregado al carrito", Toast.LENGTH_SHORT).show()
        }

        binding.sizeSmall.setOnClickListener {
            updateSizeSelection(binding.sizeSmall)
            // aquí podrías guardar la selección, ej: tamañoSeleccionado = "Pequeño"
        }

        binding.sizeMedium.setOnClickListener {
            updateSizeSelection(binding.sizeMedium)
        }

        binding.sizeLarge.setOnClickListener {
            updateSizeSelection(binding.sizeLarge)
        }
    }
    private fun OpenInicio(){
        val intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)

    }

    private fun getData() {
        val result = productoDao.getProductById(producto_id.toLong())
        if (result != null) {
            product = result
            // Aquí puedes usar product con seguridad
            binding.coffeeTitle.text = product.nombreProducto
        } else {
            // Manejar caso en que no se encuentra el producto
            binding.coffeeTitle.text = "Producto no encontrado"
        }
    }

    private fun setData(){

        binding.coffeeTitle.text = product.nombreProducto
        binding.tvDescripcion.text = product.descripcion
        binding.addToCartButton.text = "Agregar al carrito | S/${product.precio}"
        binding.tvAgregar.visibility = View.INVISIBLE
        binding.lnCaramelo.visibility = View.INVISIBLE
        binding.lnPlatano.visibility = View.INVISIBLE
        binding.lnChocolate.visibility = View.INVISIBLE
        binding.lnFresas.visibility = View.INVISIBLE

        if (product.tipo == "Cafe"){
            binding.tvAgregar.visibility = View.VISIBLE
            binding.lnCaramelo.visibility = View.VISIBLE
            binding.lnPlatano.visibility = View.VISIBLE
            binding.lnChocolate.visibility = View.VISIBLE
            binding.lnFresas.visibility = View.VISIBLE
        }
    }

    private fun updateSizeSelection(selectedButton: Button) {
        // Lista de todos los botones
        val buttons = listOf(binding.sizeSmall, binding.sizeMedium, binding.sizeLarge)

        for (button in buttons) {
            if (button == selectedButton) {
                // Botón seleccionado: fondo café, texto blanco
                button.setBackgroundColor(resources.getColor(R.color.brown, null))
                button.setTextColor(resources.getColor(android.R.color.white, null))
            } else {
                // Botones no seleccionados: fondo blanco, texto gris
                button.setBackgroundColor(resources.getColor(android.R.color.white, null))
                button.setTextColor(resources.getColor(android.R.color.darker_gray, null))
            }
        }
    }
}