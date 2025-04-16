package com.rodrigotocto.myappfirebaseauth.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "coffee_shop.db"
        private const val DATABASE_VERSION = 1

        // Definición de la tabla Producto
        const val TABLE_PRODUCTOS = "productos"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_IMAGEN = "imagen"
        const val COLUMN_TIPO = "tipo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla productos
        val CREATE_PRODUCTOS_TABLE = """
            CREATE TABLE $TABLE_PRODUCTOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_DESCRIPCION TEXT NOT NULL,
                $COLUMN_PRECIO REAL NOT NULL,
                $COLUMN_IMAGEN TEXT NOT NULL,
                $COLUMN_TIPO TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(CREATE_PRODUCTOS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // En caso de actualización, eliminar la tabla y recrearla
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        onCreate(db)
    }
}