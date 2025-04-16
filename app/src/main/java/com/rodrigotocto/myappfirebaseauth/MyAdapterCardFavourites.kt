package com.rodrigotocto.myappfirebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andrehuamani.proyectofinal.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.CardProductFavoriteBinding

class MyAdapterCardFavourites(var con: Context, var list: List<Producto>): RecyclerView.Adapter<MyAdapterCardFavourites.MyViewHolder>() {

    inner class MyViewHolder(val binding: CardProductFavoriteBinding): RecyclerView.ViewHolder(binding.root){
        var nombreProducto: TextView = binding.tvProductName
        var description: TextView = binding.tvDescription
        var imagen: ImageView = binding.imgProduct

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardProductFavoriteBinding.inflate(LayoutInflater.from(con), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nombreProducto.text = list[position].nombreProducto
        holder.description.text = list[position].descripcion
        holder.imagen.setImageResource(list[position].imageView)

    }

}