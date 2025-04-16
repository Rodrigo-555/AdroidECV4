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

    fun getUserByEmail(email: String): Usuario? {
        val selection = "${DatabaseHelper.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(email)

        db?.query(
            DatabaseHelper.TABLE_USERS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val userIdIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID)
                val emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)

                if (userIdIndex != -1 && emailIndex != -1) {
                    val userId = cursor.getLong(userIdIndex) // Cambiado a getLong
                    val userEmail = cursor.getString(emailIndex)
                    return Usuario(userId, userEmail)
                }
            }
        }

        return null
    }

}