package com.rodrigotocto.myappfirebaseauth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rodrigotocto.myappfirebaseauth.databinding.ActivitySignInBinding
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        configureGoogleSignIn()
        setListeners()
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        Log.d(TAG, "Google Sign-In configurado con WebClientId")
    }

    private fun setListeners() {
        binding.submitButton.setOnClickListener {
            signInWithEmail()
        }

        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        binding.move.setOnClickListener {
            moveToSignUp()
        }
    }

    private fun signInWithEmail() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail: success")
                    navigateToInicio()
                } else {
                    Log.w(TAG, "signInWithEmail: failure", task.exception)
                    Toast.makeText(this, "Autenticación fallida: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        Log.d(TAG, "Iniciando proceso de inicio de sesión con Google")
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(TAG, "Google Sign-In result received: ${result.resultCode}")

            val data = result.data
            if (data == null) {
                Log.e(TAG, "Sign-in intent returned null data")
                Toast.makeText(this, "Error: Intent data is null", Toast.LENGTH_SHORT).show()
                return@registerForActivityResult
            }

            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d(TAG, "Google account retrieved: ${account.email}")
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    // Log detailed error information
                    Log.e(TAG, "Google sign-in failed with code: ${e.statusCode}", e)
                    Toast.makeText(this, "Error de Google Sign-In: ${getErrorMessage(e.statusCode)}", Toast.LENGTH_LONG).show()
                }
            } else {
                Log.w(TAG, "Google sign-in failed: result code = ${result.resultCode}")
                Toast.makeText(this, "Inicio de sesión cancelado o error", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getErrorMessage(statusCode: Int): String {
        return when (statusCode) {
            GoogleSignInStatusCodes.SIGN_IN_CANCELLED -> "Cancelado por el usuario"
            GoogleSignInStatusCodes.SIGN_IN_CURRENTLY_IN_PROGRESS -> "Ya hay un inicio de sesión en progreso"
            GoogleSignInStatusCodes.SIGN_IN_FAILED -> "Falló por razón desconocida"
            GoogleSignInStatusCodes.NETWORK_ERROR -> "Error de red"
            else -> "Error código: $statusCode"
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        account?.let {
            val credential = GoogleAuthProvider.getCredential(it.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCredential: success")
                        navigateToInicio()
                    } else {
                        Log.w(TAG, "signInWithCredential: failure", task.exception)
                        Toast.makeText(this, "Error con Google Auth: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } ?: run {
            Log.w(TAG, "No se obtuvo la cuenta de Google")
            Toast.makeText(this, "No se obtuvo la cuenta de Google", Toast.LENGTH_SHORT).show()
        }
    }

    private fun moveToSignUp() {
        val intent = Intent(this, SingUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToInicio() {
        val intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)
        finish()
    }
}
