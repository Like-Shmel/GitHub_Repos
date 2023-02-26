package com.karaev.githubrepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karaev.githubrepos.databinding.ItemFeaturedBinding
import models.Favorites

class FeaturedAdapter(val authorsListener: AuthorsListener): RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> (){

    var favorites: List<Favorites> = emptyList()

    class FeaturedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var binding: ItemFeaturedBinding = ItemFeaturedBinding.bind(itemView)
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
        holder.binding!!.nameFavoritesTextview.setText(chosen.name)
        holder.binding!!.loginFavoritesTextview.setText(chosen.login)
        holder.binding!!.deleteFavorites.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                authorsListener.onDeleteClick(chosen)
            }
        })

        Glide.with(holder.itemView)
            .load(chosen.avatar)
            .into(holder.binding!!.avatarFavoritesImageview)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

interface AuthorsListener{

    fun onDeleteClick(favorites: Favorites)
}



}

