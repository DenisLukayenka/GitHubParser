package com.denis.githubparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denis.githubparser.databinding.ActivityMainBinding
import com.denis.githubparser.networks.RepositoryProvider
import com.denis.githubparser.ui.RepositoryListAdapter
import com.denis.githubparser.ui.RepositoryViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var adapter: RepositoryListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = RepositoryListAdapter(this, emptyList())
        recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        /*viewModel.allRepositories.observe(this, Observer { repos ->
            repos?.let { adapter.setRepositories(repos) }
        })*/

        viewModel.repositoryList.observe(this, Observer { repos ->
            adapter = RepositoryListAdapter(this@MainActivity, repos)
            recyclerView.adapter = adapter
        })

        binding.searchButton.setOnClickListener { searchButtonHandler() }

    }

    private fun searchButtonHandler(){

        viewModel.loadRepositories(binding.nameInput.text.toString())

        //RepositoryProvider.provideSearchRepository(binding.nameInput.text.toString())
        //viewModel.insert(GithubRepository("Dzianis2", "4", "url", "c#"))
        //viewModel.insert(GithubRepository("PPPP2", "5", "url", "c#"))


        /*val database = GithubDatabase.getInstance(applicationContext).githubDatabaseDao

        var a = database.getAllRepositories()
        var b = a.value

        //database.insert(GithubRepository(name = "name", fullName = "fullName"))

        a = database.getAllRepositories()
        var c = a.value

        var bb = database.get(0)
        var cc = database.get(1)*/

        /*val repository = RepositoryProvider.provideSearchRepository()
        repository.searchRepositories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                Log.d("Result", "There are ${result.size} Java developers in Lagos")
            }, { error ->
                error.printStackTrace()
            })*/
    }
}
