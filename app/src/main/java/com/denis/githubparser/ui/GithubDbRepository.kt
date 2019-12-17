package com.denis.githubparser.ui

import androidx.lifecycle.LiveData
import com.denis.githubparser.db.GithubDatabaseDao
import com.denis.githubparser.db.models.GithubRepository

class GithubDbRepository(private val dbDao: GithubDatabaseDao){
    val allRepositories: LiveData<List<GithubRepository>> = dbDao.getAllRepositories()

    suspend fun insertAll(reps: List<GithubRepository>){
        dbDao.insertAll(reps)
    }

    suspend fun insert(rep: GithubRepository){
        dbDao.insert(rep)
    }

    suspend fun update(rep: GithubRepository){
        dbDao.update(rep)
    }

    /*fun getByAuthor(authorName: String) : LiveData<List<GithubRepository>>{
        return dbDao.get(authorName)
    }*/
}