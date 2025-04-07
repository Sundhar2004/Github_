package com.sundhar.github.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sundhar.github.model.GitHubUser
import com.sundhar.github.model.Repo
import com.sundhar.github.repository.GitHubRepository
import kotlinx.coroutines.launch

class GitHubViewModel: ViewModel() {
    private val repository = GitHubRepository()

    val userInfo = MutableLiveData<GitHubUser>()
    val repo = MutableLiveData<List<Repo>>()

    fun fetchGitHubData(token: String){
        viewModelScope.launch {
            val response = repository.getUserInfo(token)
            if (response.isSuccessful){
                val user = response.body()
                user?.let {
                    userInfo.postValue(it)
                    fetchUserRepos(it.login)
                }
            }
        }
    }

    private fun fetchUserRepos(userName: String){
        viewModelScope.launch {
            val response = repository.getUserRepos(userName)
            if (response.isSuccessful){
                repo.postValue(response.body())
            }
        }

    }


}