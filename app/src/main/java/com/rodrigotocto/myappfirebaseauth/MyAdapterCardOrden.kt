package com.rodrigotocto.myappfirebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotocto.myappfirebaseauth.Models.Orden
import com.rodrigotocto.myappfirebaseauth.databinding.CardOrdenBinding

class MyAdapterCardOrden(var con: Context, var list: List<Orden>): RecyclerView.Adapter<MyAdapterCardOrden.MyViewHolder>() {

    inner class MyViewHolder(val binding: CardOrdenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var nombreProducto: TextView = binding.tvCoffeeName
        var cantidad: TextView = binding.tvQuantity
        var total: TextView = binding.tvItemTotal
        var precio: TextView = binding.tvCoffeePrice
        var imagen: ImageView = binding.ivCoffee
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardOrdenBinding.inflate(LayoutInflater.from(con), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nombreProducto.text = list[position].nombreProducto
        holder.cantidad.text = list[position].cantidad.toString()
        holder.total.text = list[position].total.toString()
        holder.precio.text = list[position].precio.toString()
        holder.imagen.setImageResource(list[position].imagen)

    }
}