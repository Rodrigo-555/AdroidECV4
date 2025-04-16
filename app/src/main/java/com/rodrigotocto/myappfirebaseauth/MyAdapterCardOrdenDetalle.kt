package com.rodrigotocto.myappfirebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotocto.myappfirebaseauth.Models.OrdenDetalle
import com.rodrigotocto.myappfirebaseauth.databinding.CardOrdenBinding

class MyAdapterCardOrdenDetalle(var con: Context, var list: List<OrdenDetalle>): RecyclerView.Adapter<MyAdapterCardOrdenDetalle.MyViewHolder>() {

    inner class MyViewHolder(val binding: CardOrdenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNombre: TextView = binding.tvCoffeeName
        val tvCantidad: TextView = binding.tvQuantity
        val tvSubtotal: TextView = binding.tvItemTotal
        val ivProducto: ImageView = binding.ivCoffee
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardOrdenBinding.inflate(LayoutInflater.from(con), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context

        val producto = list[position]
        holder.tvNombre.text = producto.nombre
        holder.tvCantidad.text = producto.cantidad.toString()
        holder.tvSubtotal.text = producto.subtotal.toString()
        val imageResId = context.resources.getIdentifier(producto.imageView, "drawable", context.packageName)

        if (imageResId != 0) {
            holder.ivProducto.setImageResource(imageResId)
        } else {
            // Imagen no encontrada, pon una por defecto o loguea el error
            android.util.Log.e("Adapter", "Imagen no encontrada: ${producto.imageView}")
        }


    }
}