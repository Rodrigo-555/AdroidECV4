package com.rodrigotocto.myappfirebaseauth.Models

import android.widget.ImageView

data class OrdenDetalle (
    val ordenDetalleId: Long,
    val cantidad: Int,
    val subtotal: Double,
    val productoId: Long,
    val nombre: String,
    val imageView: String
)