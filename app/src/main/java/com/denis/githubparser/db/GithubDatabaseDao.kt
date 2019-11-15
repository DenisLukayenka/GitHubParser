package com.denis.githubparser.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.denis.githubparser.db.models.GithubRepository

@Dao
interface GithubDatabaseDao{
    @Insert
    suspend fun insert(repository: GithubRepository)

    @Update
    suspend fun update(repository: GithubRepository)

    @Query("SELECT * from repositories WHERE id = :key")
    fun get(key: Long): GithubRepository?

    @Query("DELETE FROM repositories")
    suspend fun clear()

    @Query("SELECT * FROM repositories")
    fun getAllRepositories(): LiveData<List<GithubRepository>>
}