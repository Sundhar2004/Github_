package com.sundhar.github.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sundhar.github.R
import com.sundhar.github.activity.adapter.RepoAdapter
import com.sundhar.github.databinding.ActivityRepositoryListBinding
import com.sundhar.github.viewmodel.GitHubViewModel

class RepositoryListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepositoryListBinding
    private lateinit var viewModel: GitHubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRepositoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.repositoryListRv.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this)[GitHubViewModel::class.java]


         // viewModel.fetchGitHubData(token)
        viewModel.userInfo.observe(this){ user ->

        }

        viewModel.repo.observe(this){ repoList ->
            binding.repositoryListRv.adapter = RepoAdapter(repoList)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}