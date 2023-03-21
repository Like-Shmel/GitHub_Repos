package com.karaev.githubrepos.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karaev.githubrepos.GitHubReposApplication
import database.FavoritesDao
import kotlinx.coroutines.launch
import models.Favorites

class FeatureAuthorsViewModel : ViewModel() {

    val favoritesDao: FavoritesDao = GitHubReposApplication.appDatabase.favoritesDao()


    val favoritesLiveData = MutableLiveData<List<Favorites>>()

    fun updateAuthorsList() {
        viewModelScope.launch() {
            val favoritesList: List<Favorites> = favoritesDao.getAllFavorites()
            favoritesLiveData.value = favoritesList
        }
    }

    fun onDelete(favorites: Favorites) {
        viewModelScope.launch() {
            favoritesDao.deleteFavorites(favorites)
            updateAuthorsList()
        }
    }

    fun afterTextChanged(text: String) {
        viewModelScope.launch() {
            val baseList: List<Favorites> = favoritesDao.getAllFavorites()
            val newList = baseList.filter { it.name.contains(text, ignoreCase = true) }
            favoritesLiveData.value = newList
        }
    }
}