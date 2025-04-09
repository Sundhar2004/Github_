package com.sundhar.github.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.sundhar.github.model.Owner
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


        /** button for setting pag navigate*/
        binding.settingBtn.setOnClickListener {
            val mainIntent = Intent(this@RepositoryListActivity, SettingActivity::class.java)
            this@RepositoryListActivity.startActivity(mainIntent)
        }


        if (helper.isNetworkAvailable(this)) {
            /** Network Available -> API Call*/
            viewModel.fetchGitHubData(token)
            viewModel.repo.observe(this){ repoList ->
                binding.repositoryListRv.adapter = RepoAdapter(this,repoList)
            }
            viewModel.repo.observe(this) { repoList ->
                viewModel.searchRepo(binding.searchEt.text.toString()) // <-- Important
            }
            Log.d("network","online")
        } else {
            /** No Internet -> Get from Room DB */
            viewModel.offlineRepos.observe(this) { repoEntityList ->
                val repoList = repoEntityList.map { it.toRepo() }
                binding.repositoryListRv.adapter = RepoAdapter(this,repoList)
            }
            Log.d("network","offline")
        }

        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchRepo(s.toString())

            }

            override fun afterTextChanged(s: Editable?) {}
        })


        viewModel.filteredRepos.observe(this@RepositoryListActivity) { repoList ->
            binding.repositoryListRv.adapter = RepoAdapter(this@RepositoryListActivity, repoList)
        }

    }

    /** Conversion : Repo(API model) to RepoEntity(DB) */
    fun RepoEntity.toRepo(): Repo {
        return Repo(
            name = this.name,
            description = this.description,
            html_url = this.name,
            owner = Owner(this.ownerName)
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}