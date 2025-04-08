    package com.sundhar.github.viewmodel


    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.sundhar.github.model.GitHubUser
    import com.sundhar.github.model.Repo
    import com.sundhar.github.offlineMode.RepoEntity
    import com.sundhar.github.offlineMode.RepoRepository
    import com.sundhar.github.repository.GitHubRepository
    import kotlinx.coroutines.launch

    class GitHubViewModel(private val repository: GitHubRepository,
                          private val dbRepository: RepoRepository): ViewModel() {

        val userInfo = MutableLiveData<GitHubUser>()
        val repo = MutableLiveData<List<Repo>>()
        val offlineRepos: LiveData<List<RepoEntity>> = dbRepository.getAllRepos()  // Room Data

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
                                description = it.description
                            )
                        }
                        dbRepository.insertRepos(roomRepos)
                    }
                }
            }
        }
    }