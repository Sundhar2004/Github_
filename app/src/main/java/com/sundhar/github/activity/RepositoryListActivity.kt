package com.sundhar.github.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import com.sundhar.github.GitHubViewModelFactory
import com.sundhar.github.R
import com.sundhar.github.activity.adapter.RepoAdapter
import com.sundhar.github.databinding.ActivityRepositoryListBinding
import com.sundhar.github.model.Repo
import com.sundhar.github.offlineMode.RepoEntity
import com.sundhar.github.offlineMode.RepoRepository
import com.sundhar.github.repository.GitHubRepository
import com.sundhar.github.utils.helper
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

        val repository = GitHubRepository()
        val dbRepository = RepoRepository(applicationContext)

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->

            Log.d("FCM-Token", token)
        }

        binding.repositoryListRv.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, GitHubViewModelFactory(repository,dbRepository))[GitHubViewModel::class.java]
        val token = "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"

         viewModel.fetchGitHubData(token)
        viewModel.userInfo.observe(this){ user ->

        }

        if (helper.isNetworkAvailable(this)) {
            /** Network Available -> API Call*/
            viewModel.repo.observe(this){ repoList ->
                binding.repositoryListRv.adapter = RepoAdapter(repoList)
            }
            Toast.makeText(this,"You are in online!", Toast.LENGTH_SHORT).show()
        } else {
            /** No Internet -> Get from Room DB */
            viewModel.offlineRepos.observe(this) { repoEntityList ->
                val repoList = repoEntityList.map { it.toRepo() }
                binding.repositoryListRv.adapter = RepoAdapter(repoList)
            }
            Toast.makeText(this,"You are in offline!", Toast.LENGTH_SHORT).show()
        }
    }

    /** Conversion : Repo(API model) to RepoEntity(DB) */
    fun RepoEntity.toRepo(): Repo {
        return Repo(
            name = this.name,
            description = this.description,
            html_url = this.name
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}