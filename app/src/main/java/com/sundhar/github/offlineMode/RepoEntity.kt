package com.sundhar.github.offlineMode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class RepoEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int =0,
    val name: String,
    val description: String?,
)