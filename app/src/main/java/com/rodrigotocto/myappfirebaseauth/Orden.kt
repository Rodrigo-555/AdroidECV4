package com.rodrigotocto.myappfirebaseauth


data class Orden (
    val id: Long = 0,
    val nombreProducto: String,
    val precio: Double,
    val cantidad: Int,
    val total: Double,
)