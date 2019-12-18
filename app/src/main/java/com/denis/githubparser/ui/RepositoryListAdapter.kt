package com.denis.githubparser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.denis.githubparser.R
import com.denis.githubparser.db.models.GithubRepository
import org.w3c.dom.Text

class RepositoryListAdapter internal constructor(context: Context, items: List<RepositoryModel>)
    : RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var repositories = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_repository_item, parent, false)
        return RepositoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val current = repositories[position]
        holder.repositoryViewItem.text = current.repositoryName
        holder.repositoryMainLanguage.text = current.language
    }

    override fun getItemCount() = repositories.size

    inner class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val repositoryViewItem: TextView = itemView.findViewById(R.id.repositoryItem)
        val repositoryMainLanguage: TextView = itemView.findViewById(R.id.repos_language)
    }
}