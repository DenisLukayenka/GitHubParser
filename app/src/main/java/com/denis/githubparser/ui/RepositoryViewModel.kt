package com.denis.githubparser.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.denis.githubparser.db.GithubDatabase
import com.denis.githubparser.db.models.GithubRepository
import com.denis.githubparser.networks.ReposResponseModel
import com.denis.githubparser.networks.RepositoryProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryViewModel(application: Application) : AndroidViewModel(application) {
    val repositoryList = MutableLiveData<List<RepositoryModel>>()
    private var dbRepository: GithubDbRepository

    init {
        repositoryList.value = emptyList()

        val repositoriesDao = GithubDatabase.getDatabase(application, viewModelScope).githubDatabaseDao()
        dbRepository = GithubDbRepository(repositoriesDao)
    }

    fun loadRepositories(authorName: String) {
        if(checkInternetConnection()){
            val searchRepository = RepositoryProvider.provideSearchRepository()
            val call = searchRepository.searchRepositories(authorName)

            call.enqueue(object: Callback<List<ReposResponseModel>> {
                override fun onFailure(call: Call<List<ReposResponseModel>>, t: Throwable) { onRequestFailure(call, t, authorName) }
                override fun onResponse(
                    call: Call<List<ReposResponseModel>>,
                    response: Response<List<ReposResponseModel>>
                ) { onRequestSuccess(call, response, authorName) }
            })
        } else {
            Toast.makeText(getApplication(), "Network is unavailable. Getting data from database", Toast.LENGTH_LONG).show()

            viewModelScope.launch {
                repositoryList.value = dbRepository.getByAuthor(authorName)
            }
        }

    }

    fun loadAllRepositoriesFromDb(){
        viewModelScope.launch {
            val repositories = dbRepository.getAllRepositories()
            repositoryList.value = repositories
        }
    }

    private fun onRequestFailure(call: Call<List<ReposResponseModel>>, t: Throwable, authorName: String){
        Toast.makeText(getApplication(), "Network is unavailable. Get data from database", Toast.LENGTH_LONG).show()

        viewModelScope.launch {
            repositoryList.value = dbRepository.getByAuthor(authorName)
        }
    }
    private fun onRequestSuccess(call: Call<List<ReposResponseModel>>,
                                 response: Response<List<ReposResponseModel>>,
                                 authorName: String){

        val collection = response.body()
        if(collection != null && collection.count() > 0) {
            repositoryList.value = collection.map { RepositoryModel(0, it.id, authorName, it.name, it.full_name, it.url, it.language) }

            viewModelScope.launch {
                dbRepository.deleteByAuthor(authorName)
                dbRepository.insertAll(repositoryList.value!!)
            }

        } else {
            repositoryList.value = emptyList()
            Toast.makeText(getApplication(), "Cannot find user $authorName.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInternetConnection(): Boolean{
        val context: Context = getApplication()

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }
}