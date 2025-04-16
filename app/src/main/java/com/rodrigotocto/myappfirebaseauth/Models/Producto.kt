package com.rodrigotocto.myappfirebaseauth.Models

import java.io.Serializable

data class Producto(

    val id: Long = 0,
    val nombreProducto: String,
    val descripcion: String,
    val precio: Double,
    val imageView: String,
    val tipo: String

)