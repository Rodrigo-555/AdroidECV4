package com.rodrigotocto.myappfirebaseauth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.rodrigotocto.myappfirebaseauth.DAO.UsuarioDAO
import com.rodrigotocto.myappfirebaseauth.Models.Usuario
import com.rodrigotocto.myappfirebaseauth.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.submitButton.setOnClickListener {
            singUp()
        }

        binding.move.setOnClickListener {
            moveToSignIn()
        }
    }


    private fun singUp(){

        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    user?.let {
                        val usuario = Usuario(
                            email  = email,
                        )

                        val result = UsuarioDAO(this).apply {
                            open()
                        }.insertUser(usuario)

                        if (result != -1L) {
                            Toast.makeText(
                                baseContext,
                                "Usuario registrado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.e(TAG, "Error al guardar usuario en SQLite")
                        }
                    }

                    val intent2 = Intent(this, SignInActivity::class.java)
                    startActivity(intent2)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun moveToSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

}