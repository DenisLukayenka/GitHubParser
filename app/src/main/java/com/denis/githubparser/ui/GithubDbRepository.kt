package com.denis.githubparser.ui

import androidx.lifecycle.LiveData
import com.denis.githubparser.db.GithubDatabaseDao
import com.denis.githubparser.db.models.GithubRepository

class GithubDbRepository(private val dbDao: GithubDatabaseDao){
    val allRepositories: LiveData<List<GithubRepository>> = dbDao.getAllRepositories()

    suspend fun insert(rep: GithubRepository){
        dbDao.insert(rep)
    }

    suspend fun update(rep: GithubRepository){
        dbDao.update(rep)
    }
}