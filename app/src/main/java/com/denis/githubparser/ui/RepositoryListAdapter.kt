package com.denis.githubparser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.denis.githubparser.R
import com.denis.githubparser.db.models.GithubRepository

class RepositoryListAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var repositories = emptyList<GithubRepository>()

    inner class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val repositoryViewItem: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_repository_item, parent, false)
        return RepositoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val current = repositories[position]
        holder.repositoryViewItem.text = current.fullName
    }

    internal fun setRepositories(repositories: List<GithubRepository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    override fun getItemCount() = repositories.size
}