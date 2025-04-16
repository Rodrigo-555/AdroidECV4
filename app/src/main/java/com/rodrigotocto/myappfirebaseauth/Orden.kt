package com.rodrigotocto.myappfirebaseauth


data class Orden (
    val nombreProducto: String,
    val precio: Double,
    val cantidad: Int,
    val total: Double,
    val imagen: Int,
)