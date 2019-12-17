package com.denis.githubparser

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
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
        viewModel.loadAllRepositoriesFromDb()

        binding.searchButton.setOnClickListener { searchButtonHandler() }

    }

    private fun searchButtonHandler(){

        viewModel.loadRepositories(binding.nameInput.text.toString())
        hideKeyboard(this)
    }

    fun hideKeyboard(activity: Activity?){
        if (activity == null) return
        val view = activity.currentFocus
        if (view != null) {
            hideKeyboard(activity, view.windowToken)
        }
    }

    private fun hideKeyboard(activity: Activity?, windowToken: IBinder?) {
        if (activity == null) return
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
