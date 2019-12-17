package com.denis.githubparser.db

import androidx.room.*
import com.denis.githubparser.db.models.GithubRepository

@Dao
interface GithubDatabaseDao{
    @Insert
    suspend fun insert(repository: GithubRepository)

    @Insert
    suspend fun insertAll(repositories: List<GithubRepository>)

    @Query("SELECT * from repositories WHERE author_name = :authorName")
    suspend fun getByAuthor(authorName: String): List<GithubRepository>

    @Query("DELETE FROM repositories")
    suspend fun clear()

    @Query("DELETE FROM repositories WHERE author_name = :authorName")
    suspend fun deleteByAuthor(authorName: String)

    @Query("SELECT * FROM repositories")
    suspend fun getAllRepositories(): List<GithubRepository>
}