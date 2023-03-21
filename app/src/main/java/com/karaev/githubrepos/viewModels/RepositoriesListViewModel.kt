package com.karaev.githubrepos.viewModels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.Repository
import kotlinx.coroutines.launch

class RepositoriesListViewModel : ViewModel() {

//    lateinit var getUsersCall: Call<List<Repository>>

    val progressBarLiveData = MutableLiveData<Boolean>()
    val repositoryListLiveData =  MutableLiveData<List<Repository>>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun loadRepositoriesList() {
        viewModelScope.launch {
            try {

                progressBarLiveData.value = true
                val usersList = GitHubReposApplication.gitHubService.getRepositories()

                val sharedPreferences: SharedPreferences =
                    GitHubReposApplication.context.getSharedPreferences(
                        "git_hub_preferences", Context.MODE_PRIVATE
                    )

                // Получить значение по ключу

                val repositoriesEditor: String? =
                    sharedPreferences.getString("repositoriesNumber", null)
                //Установить список с новым значение

                val usersCount = if(repositoriesEditor.isNullOrBlank()){
                    usersList.size
                } else {
                    repositoriesEditor.toInt()
                }
                var repositoriesList = usersList.take(usersCount)
                repositoryListLiveData.value = repositoriesList
//                progressBarLiveData.value = false
            } catch(e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e
            }
        }
    }
}