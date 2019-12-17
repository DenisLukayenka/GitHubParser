package com.denis.githubparser.ui

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.denis.githubparser.db.GithubDatabase
import com.denis.githubparser.db.models.GithubRepository
import com.denis.githubparser.networks.RepositoryProvider
import io.reactivex.Observable
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {
    val repositoryList = MutableLiveData<List<GithubRepository>>()

    init {
        val repositoriesDao = GithubDatabase.getDatabase(application, viewModelScope).githubDatabaseDao()
    }

    fun loadRepositories(authorName: String) {
        val searchRepository = RepositoryProvider.provideSearchRepository()
        val call = searchRepository.searchRepositories(authorName)

        call.enqueue(object: Callback<List<GithubRepository>> {
            override fun onFailure(call: Call<List<GithubRepository>>, t: Throwable) { onRequestFailure(call, t, authorName) }
            override fun onResponse(
                call: Call<List<GithubRepository>>,
                response: Response<List<GithubRepository>>
            ) { onRequestSuccess(call, response, authorName) }
        })
    }

    private fun onRequestFailure(call: Call<List<GithubRepository>>, t: Throwable, authorName: String){
        repositoryList.value = emptyList()
        Toast.makeText(getApplication(), "Error occurred while search user $authorName.", Toast.LENGTH_LONG).show()
    }
    private fun onRequestSuccess(call: Call<List<GithubRepository>>,
                                 response: Response<List<GithubRepository>>,
                                 authorName: String){

        val collection = response.body()
        if(collection != null && collection.count() > 0) {
            repositoryList.value = response.body()
            repositoryList.value?.forEach { el -> el.authorName = authorName }
        } else {
            repositoryList.value = emptyList()
            Toast.makeText(getApplication(), "Cannot find user $authorName.", Toast.LENGTH_SHORT).show()
        }
    }
}