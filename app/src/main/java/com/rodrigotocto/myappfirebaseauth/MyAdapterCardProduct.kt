package com.rodrigotocto.myappfirebaseauth

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.CardProductBinding

class MyAdapterCardProduct (var con: Context, var list: List<Producto>): RecyclerView.Adapter<MyAdapterCardProduct.MyViewHolder>() {

    inner class MyViewHolder(val binding: CardProductBinding) : RecyclerView.ViewHolder(binding.root) {
        var productoId: TextView = binding.productoId
        var nombreProducto: TextView = binding.tvProductName
        var description: TextView = binding.tvDescription
        var price: TextView = binding.tvPrice
        var imagen: ImageView = binding.imgProduct
        fun bind(productoId: String) {
            val btnVer = itemView.findViewById<ImageButton>(R.id.btnAdd)

            btnVer.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, DetalleProductoActivity::class.java)
                intent.putExtra("productoId", productoId) // Asegurate que Producto sea Serializable o Parcelable
                context.startActivity(intent)
            }
        }
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

        val producto = list[position]
        holder.productoId.text = producto.id.toString()
        holder.nombreProducto.text = producto.nombreProducto
        holder.description.text = producto.descripcion
        holder.price.text = producto.precio.toString()

        val imageResId = context.resources.getIdentifier(producto.imageView, "drawable", context.packageName)
        holder.imagen.setImageResource(imageResId)

        holder.bind(producto.id.toString())


    }

    // Método para actualizar la lista de productos
    fun updateList(newList: List<Producto>) {
        list = newList
        notifyDataSetChanged()
    }

}