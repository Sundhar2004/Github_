package com.sundhar.github.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.sundhar.github.R
import com.sundhar.github.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private  lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.settingLayout.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
                val mainIntent = Intent(this@SettingActivity, LoginActivity::class.java)
                this@SettingActivity.startActivity(mainIntent)
                this@SettingActivity.finish()

                Toast.makeText(this, "Signed Out Successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }
}