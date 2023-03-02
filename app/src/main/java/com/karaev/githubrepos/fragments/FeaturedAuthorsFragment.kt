package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.karaev.githubrepos.FeaturedAdapter
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.RepositoriesListScreen
import com.karaev.githubrepos.databinding.FragmentFeaturedAuthorsBinding
import database.FavoritesDao
import models.Favorites

class FeaturedAuthorsFragment: Fragment(R.layout.fragment_featured_authors) {

    private var binding: FragmentFeaturedAuthorsBinding? = null

    val favoritesDao: FavoritesDao = GitHubReposApplication.appDatabase.favoritesDao()

    val featuredAdapter = FeaturedAdapter(
        authorsListener = object: FeaturedAdapter.AuthorsListener{
            override fun onDeleteClick(favorites: Favorites) {
               favoritesDao.deleteFavorites(favorites)
                updateAuthorsList()
            }

        }
    )
//    val favoritesList: List<Favorites> = FavoritesDao.getAllFavorites()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFeaturedAuthorsBinding.bind(view)

        binding!!.favoritesBackToolbar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                GitHubReposApplication.navigator.goForward(RepositoriesListScreen())
            }

        })

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.favoritesRecyclerview.addItemDecoration(divider)

        binding!!.favoritesRecyclerview.adapter = featuredAdapter
        updateAuthorsList()

        val baseList: List<Favorites> = featuredAdapter.favorites

        binding!!.favoritesEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                val text: String = s.toString()
                val newList = baseList.filter { it.name.contains(text, ignoreCase = true) }

                featuredAdapter.favorites = newList
                featuredAdapter.notifyDataSetChanged()
            }
        })
    }


    fun updateAuthorsList(){
        val  favoritesList : List<Favorites> = favoritesDao.getAllFavorites()
        featuredAdapter.favorites = favoritesList
        featuredAdapter.notifyDataSetChanged()
    }



}


