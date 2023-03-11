package com.karaev.githubrepos.viewModels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.UserDetails
import retrofit2.Call
import retrofit2.Response

class UserDetailsViewModel : ViewModel() {

    lateinit var getUsersDetails : Call<UserDetails>
    val userDetailsLiveData = MutableLiveData<UserDetails>()
    val errorLiveData = MutableLiveData<String>()

    fun loadUserDetails(usersLogin: String){
        getUsersDetails = GitHubReposApplication.gitHubService.getUsersDetails(usersLogin)


        getUsersDetails.enqueue(object : retrofit2.Callback<UserDetails> {
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                val userDetails: UserDetails? = response.body()
                userDetailsLiveData.value = userDetails

            }

            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                    errorLiveData.value = t.message!!

            }
        })
}

}