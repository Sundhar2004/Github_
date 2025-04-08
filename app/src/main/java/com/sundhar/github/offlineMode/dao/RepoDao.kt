package com.sundhar.github.offlineMode.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sundhar.github.offlineMode.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("SELECT * FROM repo_table")
    fun getAllRepos(): LiveData<List<RepoEntity>>

    @Query("DELETE FROM repo_table")
    suspend fun clearRepos()
}