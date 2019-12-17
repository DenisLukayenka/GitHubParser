package com.denis.githubparser.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.denis.githubparser.db.models.GithubRepository

@Dao
interface GithubDatabaseDao{
    @Insert
    suspend fun insert(repository: GithubRepository)

    @Insert
    suspend fun insertAll(repositories: List<GithubRepository>)

    @Update
    suspend fun update(repository: GithubRepository)

    /*@Query("SELECT * from repositories WHERE authorName = :authorName")
    fun get(authorName: String): LiveData<List<GithubRepository>>

    @Query("SELECT * from repositories WHERE authorName = :authorName AND repository_language = :language")
    fun getByAuthorAndLanguage(authorName: String, language: String): LiveData<List<GithubRepository>>*/

    @Query("DELETE FROM repositories")
    suspend fun clear()

    @Query("SELECT * FROM repositories")
    fun getAllRepositories(): LiveData<List<GithubRepository>>
}