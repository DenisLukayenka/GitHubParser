package com.denis.githubparser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.denis.githubparser.db.GithubDatabase
import com.denis.githubparser.db.models.GithubRepository
import kotlinx.coroutines.launch

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GithubDbRepository
    val allRepositories: LiveData<List<GithubRepository>>

    init {
        val repositoriesDao = GithubDatabase.getDatabase(application, viewModelScope).githubDatabaseDao()
        repository = GithubDbRepository(repositoriesDao)
        allRepositories = repository.allRepositories
    }

    fun insert(repos: GithubRepository) = viewModelScope.launch {
        repository.insert(repos)
    }

    fun update(repos: GithubRepository) = viewModelScope.launch {
        repository.update(repos)
    }
}