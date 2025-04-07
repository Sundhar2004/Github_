package com.sundhar.github.activity.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.sundhar.github.R
import com.sundhar.github.model.Repo

class RepoAdapter(private val repoList: List<Repo>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_repo, parent, false)
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        Log.e("repoList_adp", Gson().toJson(repo))
        holder.repoName.text = repo.name
        holder.repoDesc.text = repo.description ?: "Description Unavailable"
    }

    override fun getItemCount() = repoList.size

    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView = view.findViewById(R.id.repoName)
        val repoDesc: TextView = view.findViewById(R.id.repoDesc)
    }
}
