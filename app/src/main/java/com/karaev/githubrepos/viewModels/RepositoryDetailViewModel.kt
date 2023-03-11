package com.karaev.githubrepos.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.RepositoryDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailViewModel : ViewModel() {

    lateinit var getRepositoriesDetails : Call<RepositoryDetails>
    val repositoryDetailsLiveData = MutableLiveData<RepositoryDetails>()
    val errorLiveData = MutableLiveData<String>()

    fun loadRepositoryDetails(repository: Int) {
        getRepositoriesDetails =
            GitHubReposApplication.gitHubService.getRepositoriesDetails(repository)

        getRepositoriesDetails.enqueue(object : Callback<RepositoryDetails> {
            override fun onResponse(
                call: Call<RepositoryDetails>, response: Response<RepositoryDetails>
            ) {
                val repositoryDetails: RepositoryDetails = response.body()!!
                repositoryDetailsLiveData.value = repositoryDetails
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {

                errorLiveData.value = t.message!!
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getRepositoriesDetails.cancel()
    }
}