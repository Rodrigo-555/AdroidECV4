package com.rodrigotocto.myappfirebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.CardProductBinding

class MyAdapterCardProduct (var con: Context, var list: List<Producto>): RecyclerView.Adapter<MyAdapterCardProduct.MyViewHolder>() {

    inner class MyViewHolder(val binding: CardProductBinding) : RecyclerView.ViewHolder(binding.root) {
        var nombreProducto: TextView = binding.tvProductName
        var description: TextView = binding.tvDescription
        var price: TextView = binding.tvPrice
        var imagen: ImageView = binding.imgProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardProductBinding.inflate(LayoutInflater.from(con), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context

        holder.nombreProducto.text = list[position].nombreProducto
        holder.description.text = list[position].descripcion
        holder.price.text = list[position].precio.toString()

        val imageResId = context.resources.getIdentifier(list[position].imageView, "drawable", context.packageName)
        holder.imagen.setImageResource(imageResId)
    }

    // Método para actualizar la lista de productos
    fun updateList(newList: List<Producto>) {
        list = newList
        notifyDataSetChanged()
    }

}