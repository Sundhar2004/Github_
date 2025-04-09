package com.sundhar.github.model

data class Repo(
    val name: String,
    val html_url: String,
    val description: String?,
    val owner: Owner
)


data class Owner(
    val login: String
)