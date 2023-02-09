package com.karaev.githubrepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepositoryAdapter(val repositoryListener: RepositoryListener): RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: MutableList<Repository> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_repository,
            parent,
            false
        )

        return RepositoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository: Repository = repositories.get(position)
        holder.nameTextView.setText(repository.name)
        holder.loginTextView.setText(repository.owner.login)
        holder.descriptionTextView.setText(repository.description)
//        val repositoryDetails: RepositoryDetails = repositoryDetails.get(position)


        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                repositoryListener.onItemClick(repository)
            }

        })
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.name_textview)
        val loginTextView: TextView = itemView.findViewById(R.id.login_textview)
        val descriptionTextView: TextView = itemView.findViewById(R.id.description_textview)
    }

    interface RepositoryListener{
//        fun onItemClick(repositories: Repository)
        fun onItemClick(repository: Repository)
    }
}
