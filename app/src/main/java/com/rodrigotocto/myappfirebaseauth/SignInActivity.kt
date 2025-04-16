package com.rodrigotocto.myappfirebaseauth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.rodrigotocto.myappfirebaseauth.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var  binding: ActivitySignInBinding
    private lateinit var  auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.submitButton.setOnClickListener{
            singIn()
        }

        binding.move.setOnClickListener{
            moveToSignUp()
        }

    }

    private fun singIn(){
        auth.signInWithEmailAndPassword(binding.email.getText().toString().trim(), binding.password.getText().toString().trim())
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, InicioActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private  fun moveToSignUp(){
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

}