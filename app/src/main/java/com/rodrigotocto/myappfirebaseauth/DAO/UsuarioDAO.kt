package com.rodrigotocto.myappfirebaseauth.DAO

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rodrigotocto.myappfirebaseauth.Database.DatabaseHelper
import com.rodrigotocto.myappfirebaseauth.Models.Usuario

class UsuarioDAO( context : Context) {

    private val dbHelper = DatabaseHelper(context)
    private var db: SQLiteDatabase? = null

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    // Insertar un Usuario
    fun insertUser(usuario: Usuario): Long {
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_EMAIL,usuario.email)
        }
        return db?.insert(DatabaseHelper.TABLE_USERS, null, values) ?: -1
    }




}