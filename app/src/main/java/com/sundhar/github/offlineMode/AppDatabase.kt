package com.sundhar.github.offlineMode

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sundhar.github.offlineMode.dao.RepoDao

@Database(entities = [RepoEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun repDao() : RepoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "repo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}