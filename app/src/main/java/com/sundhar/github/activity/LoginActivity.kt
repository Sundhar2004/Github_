package com.sundhar.github.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sundhar.github.R
import com.sundhar.github.databinding.ActivityLoginBinding
import com.sundhar.github.databinding.ActivityMainBinding
import com.sundhar.github.utils.GoogleSignInHelper
import com.sundhar.github.utils.helper

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val RC_SIGN_IN = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (helper.isNetworkAvailable(this)) {
            GoogleSignInHelper.initGoogleSignIn(this)
            binding.googleSignInBtn.setOnClickListener {
                /*val mainIntent = Intent(this@LoginActivity, RepositoryListActivity::class.java)
                startActivity(mainIntent)
                finish()*/

                val signInIntent = GoogleSignInHelper.getSignInIntent()
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }else{
            Toast.makeText(this, "Please check your network connectivity!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    // Navigate to RepositoryListActivity
                    val mainIntent = Intent(this@LoginActivity, RepositoryListActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                } else {
                    Log.e("TAG", "Firebase Auth Failed: ${task.exception}")
                }
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)
                    if (account != null) {
                        firebaseAuthWithGoogle(account.idToken!!)
                    } else {
                        Log.e("TAG", "Google Sign-In failed: Account is null")
                    }
                } catch (e: ApiException) {
                    Log.e("TAG", "Google Sign-In failed with code = ${e.statusCode}")
                }
            } else {
                Log.e("TAG", "Google Sign-In Task Failed: ${task.exception}")
            }
        }
    }

}