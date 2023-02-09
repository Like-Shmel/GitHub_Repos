package com.karaev.githubrepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import models.Favorites

class FeaturedAdapter(val authorsListener: AuthorsListener): RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> (){


    var favorites: List<Favorites> = emptyList()

    class FeaturedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val avatarFavorites: ImageView = itemView.findViewById(R.id.avatar_favorites_imageview)
        val nameFavorites: TextView = itemView.findViewById(R.id.name_favorites_textview)
        val loginFavorites: TextView = itemView.findViewById(R.id.login_favorites_textview)
        val delete: ImageView = itemView.findViewById(R.id.delete_favorites)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
       val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_featured,
            parent,
            false
        )
        return  FeaturedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
       val chosen: Favorites = favorites.get(position)
        holder.nameFavorites.setText(chosen.name)
        holder.loginFavorites.setText(chosen.login)
        holder.delete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                authorsListener.onDeleteClick(chosen)
            }
        })

        Glide.with(holder.itemView)
            .load(chosen.avatar)
            .into(holder.avatarFavorites)
    }


    override fun getItemCount(): Int {
        return favorites.size
    }



interface AuthorsListener{

    fun onDeleteClick(favorites: Favorites)
}



}

