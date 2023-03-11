package com.karaev.githubrepos.viewModels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karaev.githubrepos.GitHubReposApplication

class SettingsViewModel : ViewModel() {

    val numberOfRepositoriesLiveData = MutableLiveData<String>()

    fun setNumber() {
        val sharedPreferences: SharedPreferences =
            GitHubReposApplication.context.getSharedPreferences(
                "git_hub_preferences",
                Context.MODE_PRIVATE
            )


        val numberOfRepositories: String? = sharedPreferences.getString("repositoriesNumber", null)
        numberOfRepositoriesLiveData.value = numberOfRepositories
    }

    fun saveSettings(repositoriesList : String) {
        val sharedPreferences: SharedPreferences = GitHubReposApplication.context.getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )


        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositoriesNumber", repositoriesList)
        editor.apply()
    }

}