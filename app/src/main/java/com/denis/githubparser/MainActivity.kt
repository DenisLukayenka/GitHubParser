package com.denis.githubparser

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denis.githubparser.databinding.ActivityMainBinding
import com.denis.githubparser.db.models.GithubRepository
import com.denis.githubparser.ui.RepositoryListAdapter
import com.denis.githubparser.ui.RepositoryViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RepositoryViewModel
    private val newRepositoryActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.button.setOnClickListener{ onButtonClick() }

        val adapter = RepositoryListAdapter(this)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(RepositoryViewModel::class.java)
        viewModel.allRepositories.observe(this, Observer { repos ->
            repos?.let { adapter.setRepositories(repos) }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newRepositoryActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.getStringExtra(NewRepositoryTemplate.EXTRA_REPLY)?.let {
                val repository = GithubRepository(name = it, fullName = "full")
                viewModel.insert(repository)
            }
        } else {
            Toast.makeText(applicationContext, "Empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun onButtonClick(){
        binding.textView.text = "AA"

        var a = viewModel.allRepositories.value


        val intent = Intent(this@MainActivity, NewRepositoryTemplate::class.java)
        startActivityForResult(intent, newRepositoryActivityRequestCode)

        /*val database = GithubDatabase.getInstance(applicationContext).githubDatabaseDao

        var a = database.getAllRepositories()
        var b = a.value

        //database.insert(GithubRepository(name = "name", fullName = "fullName"))

        a = database.getAllRepositories()
        var c = a.value

        var bb = database.get(0)
        var cc = database.get(1)

        val repository = RepositoryProvider.provideSearchRepository()
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
