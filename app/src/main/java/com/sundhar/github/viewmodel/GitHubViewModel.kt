    package com.sundhar.github.viewmodel


    import android.content.Context
    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.map
    import androidx.lifecycle.viewModelScope
    import com.sundhar.github.model.GitHubUser
    import com.sundhar.github.model.Repo
    import com.sundhar.github.offlineMode.RepoEntity
    import com.sundhar.github.offlineMode.RepoRepository
    import com.sundhar.github.repository.GitHubRepository
    import com.sundhar.github.utils.helper
    import kotlinx.coroutines.launch

    class GitHubViewModel(private val repository: GitHubRepository,
                          private val dbRepository: RepoRepository): ViewModel() {

        val userInfo = MutableLiveData<GitHubUser>()
        val repo = MutableLiveData<List<Repo>>()
        val offlineRepos: LiveData<List<RepoEntity>> = dbRepository.getAllRepos()  // Room Data

        val filteredRepos = MutableLiveData<List<Repo>>()


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
                    val repoList = response.body()
                    repo.postValue(repoList!!)

                    repoList.let { repoList ->
                        val roomRepos = repoList.map {
                            RepoEntity(
                                name = it.name,
                                description = it.description,
                                ownerName = it.owner.login
                            )
                        }
                        dbRepository.insertRepos(roomRepos)
                    }
                }
            }
        }

        fun searchRepo(query: String) {
            val repoList = repo.value ?: emptyList()
            if (query.isEmpty()) {
                filteredRepos.postValue(repoList)
            } else {
                val filteredList = repoList.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.owner.login.contains(query, ignoreCase = true)
                }
                filteredRepos.postValue(filteredList)
            }
        }

    }