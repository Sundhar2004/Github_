package com.sundhar.github

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sundhar.github.offlineMode.RepoRepository
import com.sundhar.github.repository.GitHubRepository
import com.sundhar.github.viewmodel.GitHubViewModel

class GitHubViewModelFactory(
    private val repository: GitHubRepository,
    private val dbRepository: RepoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GitHubViewModel::class.java)) {
            return GitHubViewModel(repository, dbRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}