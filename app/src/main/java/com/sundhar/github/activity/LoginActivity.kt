package com.sundhar.github.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sundhar.github.R
import com.sundhar.github.databinding.ActivityLoginBinding
import com.sundhar.github.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.googleSignInBtn.setOnClickListener {
            val mainIntent = Intent(this@LoginActivity, RepositoryListActivity::class.java)
            this@LoginActivity.startActivity(mainIntent)
            this@LoginActivity.finish()
            Toast.makeText(this,"Working in progress", Toast.LENGTH_SHORT).show()
        }

    }
}