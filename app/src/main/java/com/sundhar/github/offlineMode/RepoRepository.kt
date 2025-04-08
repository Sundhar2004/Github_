package com.sundhar.github.offlineMode

import android.content.Context
import androidx.lifecycle.LiveData


class RepoRepository(context: Context) {
    private val repoDao = AppDatabase.getDatabase(context).repDao()

    suspend fun insertRepos(repos: List<RepoEntity>) {
        repoDao.clearRepos()
        repoDao.insertRepos(repos)
    }

    fun getAllRepos(): LiveData<List<RepoEntity>> {
        return repoDao.getAllRepos()
    }

}