package com.rodrigotocto.myappfirebaseauth.DAO

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.rodrigotocto.myappfirebaseauth.Database.DatabaseHelper
import com.rodrigotocto.myappfirebaseauth.Models.Producto

class ProductoDAO(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private var db: SQLiteDatabase? = null

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun getProductById(id: Long): Producto? {
        val cursor = db?.query(
            DatabaseHelper.TABLE_PRODUCTOS,
            null,
            "${DatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return cursorToProducto(it)
            }
        }
        return null
    }

    // Obtener todos los productos
    fun getAllProducts(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val cursor = db?.query(
            DatabaseHelper.TABLE_PRODUCTOS,
            null, null, null, null, null,
            "${DatabaseHelper.COLUMN_NOMBRE} ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                productos.add(cursorToProducto(it))
            }
        }
        return productos
    }

    // Obtener productos por tipo
    fun getProductsByType(tipo: String): List<Producto> {
        val productos = mutableListOf<Producto>()
        val cursor = db?.query(
            DatabaseHelper.TABLE_PRODUCTOS,
            null,
            "${DatabaseHelper.COLUMN_TIPO} = ?",
            arrayOf(tipo),
            null, null,
            "${DatabaseHelper.COLUMN_NOMBRE} ASC"
        )

        cursor?.use {
            while (it.moveToNext()) {
                productos.add(cursorToProducto(it))
            }
        }
        return productos
    }

    // Convertir un cursor a objeto Producto
    private fun cursorToProducto(cursor: Cursor): Producto {
        val idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)
        val nombreIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)
        val descripcionIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPCION)
        val precioIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PRECIO)
        val imagenIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGEN)
        val tipoIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TIPO)

        return Producto(
            id = cursor.getLong(idIndex),
            nombreProducto = cursor.getString(nombreIndex),
            descripcion = cursor.getString(descripcionIndex),
            precio = cursor.getDouble(precioIndex),
            imageView = cursor.getString(imagenIndex),
            tipo = cursor.getString(tipoIndex)
        )
    }
}