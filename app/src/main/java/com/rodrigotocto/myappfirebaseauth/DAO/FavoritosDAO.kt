package com.rodrigotocto.myappfirebaseauth.DAO

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.rodrigotocto.myappfirebaseauth.Database.DatabaseHelper
import com.rodrigotocto.myappfirebaseauth.Models.Favoritos
import com.rodrigotocto.myappfirebaseauth.Models.Producto

class FavoritosDAO (context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private var db: SQLiteDatabase? = null

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    // Añadir un producto a favoritos
    fun addFavorite(userId: String, productId: Long): Long {
        val values = android.content.ContentValues().apply {
            put(DatabaseHelper.COLUMN_USER_ID, userId)
            put(DatabaseHelper.COLUMN_PRODUCT_ID, productId.toString())
        }
        return db?.insert(DatabaseHelper.TABLE_FAVORITES, null, values) ?: -1
    }

    // Eliminar un producto de favoritos
    fun removeFavorite(userId: String, productId: Long): Int {
        return db?.delete(
            DatabaseHelper.TABLE_FAVORITES,
            "${DatabaseHelper.COLUMN_USER_ID} = ? AND ${DatabaseHelper.COLUMN_PRODUCT_ID} = ?",
            arrayOf(userId, productId.toString())
        ) ?: 0
    }

    fun getUserFavoriteProducts(userId: String): List<Producto> {
        val productos = mutableListOf<Producto>()

        // Consulta SQL para obtener los productos favoritos de un usuario
        val query = """
            SELECT p.* FROM ${DatabaseHelper.TABLE_PRODUCTOS} p
            INNER JOIN ${DatabaseHelper.TABLE_FAVORITES} f 
            ON p.${DatabaseHelper.COLUMN_ID} = f.${DatabaseHelper.COLUMN_PRODUCT_ID}
            WHERE f.${DatabaseHelper.COLUMN_USER_ID} = ?
            ORDER BY p.${DatabaseHelper.COLUMN_NOMBRE} ASC
        """

        val cursor = db?.rawQuery(query, arrayOf(userId))

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