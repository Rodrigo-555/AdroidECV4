package com.rodrigotocto.myappfirebaseauth.Models


data class Orden (
    val nombreProducto: String,
    val precio: Double,
    val cantidad: Int,
    val total: Double,
    val imagen: Int,
)