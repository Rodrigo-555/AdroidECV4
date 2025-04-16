package com.rodrigotocto.myappfirebaseauth.Models


data class Orden (
    val id: Long = 0,
    val nombreProducto: String,
    val precio: Double,
    val cantidad: Int,
    val total: Double,
)