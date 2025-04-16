package com.rodrigotocto.myappfirebaseauth

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotocto.myappfirebaseauth.DAO.FavoritosDAO
import com.rodrigotocto.myappfirebaseauth.Models.Producto
import com.rodrigotocto.myappfirebaseauth.databinding.CardProductBinding

class MyAdapterCardProduct (var con: Context, var list: List<Producto>, private val userId: Long ): RecyclerView.Adapter<MyAdapterCardProduct.MyViewHolder>() {

    private val favoritosDAO = FavoritosDAO(con)
    init {
        favoritosDAO.open()
    }
    fun cleanup() {
        favoritosDAO.close()
    }

    inner class MyViewHolder(val binding: CardProductBinding) : RecyclerView.ViewHolder(binding.root) {
        var nombreProducto: TextView = binding.tvProductName
        var description: TextView = binding.tvDescription
        var price: TextView = binding.tvPrice
        var imagen: ImageView = binding.imgProduct
        var btnFavorite: ImageButton = binding.btnFavorite
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

        holder.nombreProducto.text = list[position].nombreProducto
        holder.description.text = list[position].descripcion
        holder.price.text = list[position].precio.toString()

        val imageResId = context.resources.getIdentifier(list[position].imageView, "drawable", context.packageName)
        holder.imagen.setImageResource(imageResId)

        val isFavorite = favoritosDAO.isProductFavorite(userId, producto.id)
        updateFavoriteButtonAppearance(holder.btnFavorite, isFavorite)

        holder.btnFavorite.setOnClickListener {
            val currentlyFavorite = favoritosDAO.isProductFavorite(userId, producto.id)

            if (currentlyFavorite) {
                favoritosDAO.removeFavorite(userId, producto.id)
                updateFavoriteButtonAppearance(holder.btnFavorite, false)
            } else {
                favoritosDAO.addFavorite(userId, producto.id)
                updateFavoriteButtonAppearance(holder.btnFavorite, true)
            }
        }
    }

    private fun updateFavoriteButtonAppearance(button: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            button.setColorFilter(ContextCompat.getColor(con, R.color.favorite_red))
        } else {
            button.setColorFilter(ContextCompat.getColor(con, R.color.favorite_gray))
        }
    }
    // Metodo para actualizar la lista de productos
    fun updateList(newList: List<Producto>) {
        list = newList
        notifyDataSetChanged()
    }

}