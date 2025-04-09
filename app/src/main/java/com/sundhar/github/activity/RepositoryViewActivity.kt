package com.sundhar.github.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sundhar.github.R
import com.sundhar.github.databinding.ActivityRepositoryViewBinding

class RepositoryViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepositoryViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRepositoryViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("title")
        val owner = intent.getStringExtra("owner")
        val description = intent.getStringExtra("description")

        binding.titleTv.text = title.toString()
        binding.ownerNameTv.text = owner.toString()
        binding.descriptionTv.text = description.toString()
    }
}