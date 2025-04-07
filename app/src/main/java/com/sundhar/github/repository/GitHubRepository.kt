package com.sundhar.github.repository

import com.sundhar.github.api.RetrofitInstance

class GitHubRepository {

    suspend fun getUserInfo(token: String) = RetrofitInstance.api.getAuthenticatedUser("token $token")

    suspend fun getUserRepos(username: String) = RetrofitInstance.api.getUserRepositories(username)
}