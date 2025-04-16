package com.rodrigotocto.myappfirebaseauth

import com.rodrigotocto.myappfirebaseauth.Models.OrdenDetalle

object CartManager {
    val cartItems = mutableListOf<OrdenDetalle>()

    fun addItem(item: OrdenDetalle) {
        val index = cartItems.indexOfFirst { it.productoId == item.productoId }
        if (index >= 0) {
            val existingItem = cartItems[index]
            val updated = existingItem.copy(
                cantidad = existingItem.cantidad + item.cantidad,
                subtotal = existingItem.subtotal + item.subtotal
            )
            cartItems[index] = updated
        } else {
            cartItems.add(item)
        }
    }

    fun clearCart() {
        cartItems.clear()
    }
    fun removeItem(productoId: Long) {
        cartItems.removeAll { it.productoId == productoId }
    }

    fun getTotalItems(): Int = cartItems.sumOf { it.cantidad }
}
