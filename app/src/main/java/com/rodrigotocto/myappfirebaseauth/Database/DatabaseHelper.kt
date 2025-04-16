package com.rodrigotocto.myappfirebaseauth.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "coffee_shop.db"
        private const val DATABASE_VERSION = 3

        // Definición de la tabla Producto
        const val TABLE_PRODUCTOS = "productos"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_IMAGEN = "imagen"
        const val COLUMN_TIPO = "tipo"

        // Tabla Usuarios
        const val TABLE_USERS = "usuarios"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_EMAIL = "email"

        // Tabla Favoritos
        const val TABLE_FAVORITES = "favoritos"
        const val COLUMN_FAVORITE_ID = "favorite_id"
        const val COLUMN_PRODUCT_ID = "product_id"

        // Tabla OrdenCabecera
        const val TABLE_ORDENES_CABECERA = "ordenesCabecera"
        const val COLUMN_ORDEN_CABECERA_ID = "orden_cabecera_id"
        const val COLUMN_TOTAL = "total_orden"
        const val COLUMN_USUARIO_ID = "usuario_id"

        // Tabla OrdenDetalle
        const val TABLE_ORDENES_DETALLE = "ordenesDetalle"
        const val COLUMN_ORDEN_DETALLE_ID = "orden_detalle_id"
        const val COLUMN_CANTIDAD = "cantidad"
        const val COLUMN_SUBTOTAL = "subtotal"
        const val COLUMN_PRODUCTO_ID = "producto_id"





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

        val CREATE_USERS_TABLE = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EMAIL TEXT NOT NULL
            )
        """.trimIndent()

        val CREATE_FAVORITES_TABLE = """
            CREATE TABLE $TABLE_FAVORITES (
                $COLUMN_FAVORITE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_PRODUCT_ID INTEGER NOT NULL
            )
        """.trimIndent()

        val CREATE_ORDENES_CABECERA_TABLE = """
            CREATE TABLE $TABLE_ORDENES_CABECERA (
                $COLUMN_ORDEN_CABECERA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TOTAL DECIMAL NOT NULL,
                $COLUMN_USUARIO_ID INTEGER NOT NULL
            )
        """.trimIndent()

        val CREATE_ORDENES_DETALLE_TABLE = """
            CREATE TABLE $TABLE_ORDENES_DETALLE (
                $COLUMN_ORDEN_DETALLE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CANTIDAD INTEGER NOT NULL,
                $COLUMN_SUBTOTAL DECIMAL NOT NULL,
                $COLUMN_PRODUCTO_ID INTEGER NOT NULL
            )
        """.trimIndent()



        db.execSQL(CREATE_PRODUCTOS_TABLE)
        db.execSQL(CREATE_USERS_TABLE)
        db.execSQL(CREATE_FAVORITES_TABLE)
        db.execSQL(CREATE_ORDENES_CABECERA_TABLE)
        db.execSQL(CREATE_ORDENES_DETALLE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // En caso de actualización, eliminar la tabla y recrearla
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDENES_CABECERA")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDENES_DETALLE")
        onCreate(db)
    }
}