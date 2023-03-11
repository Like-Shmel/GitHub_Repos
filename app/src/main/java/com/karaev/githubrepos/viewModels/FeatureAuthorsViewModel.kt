package com.karaev.githubrepos.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karaev.githubrepos.GitHubReposApplication
import database.FavoritesDao
import models.Favorites

class FeatureAuthorsViewModel : ViewModel() {

    val favoritesDao: FavoritesDao = GitHubReposApplication.appDatabase.favoritesDao()
    val baseList: List<Favorites> = favoritesDao.getAllFavorites()

    val favoritesLiveData = MutableLiveData<List<Favorites>>()

    fun updateAuthorsList(){
        val  favoritesList : List<Favorites> = favoritesDao.getAllFavorites()
        favoritesLiveData.value = favoritesList
    }


    fun onDelete(favorites: Favorites) {
        favoritesDao.deleteFavorites(favorites)
        updateAuthorsList()
    }

    fun afterTextChanged(text: String) {
        val newList = baseList.filter { it.name.contains(text, ignoreCase = true) }
        favoritesLiveData.value = newList
    }
}