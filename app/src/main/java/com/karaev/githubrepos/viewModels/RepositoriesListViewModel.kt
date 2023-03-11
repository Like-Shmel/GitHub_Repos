package com.karaev.githubrepos.viewModels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesListViewModel : ViewModel() {

    lateinit var getUsersCall: Call<List<Repository>>

    val progressBarLiveData = MutableLiveData<Boolean>()
    val repositoryListLiveData =  MutableLiveData<List<Repository>>()
    val errorLiveData = MutableLiveData<Throwable>()

    fun loadRepositoriesList() {

        progressBarLiveData.value = true
        getUsersCall = GitHubReposApplication.gitHubService.getRepositories()

        getUsersCall.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>, response: Response<List<Repository>>
            ) {


                if (response.isSuccessful) {
                    val usersList: List<Repository> = response.body()!!

                    // Обратиться к хранилищу со значениями

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
//                    repositoryAdapter.repositories = repositoriesList.toMutableList()
//                    repositoryAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                progressBarLiveData.value = false
                errorLiveData.value = t
//                binding!!.reposListProgressBar.visibility = View.GONE
//
//                val errorSnackBar = Snackbar.make(
//                    requireView(), t.message!!, Snackbar.LENGTH_LONG
//                )
//                errorSnackBar.show()
            }
        })

    }

    override fun onCleared() {
        //отменяет запрос
        super.onCleared()
        getUsersCall.cancel()
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        getUsersCall.cancel()
//    }
}