package com.karaev.githubrepos

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("repositories/{id}")
    fun getRepositoriesDetails(
        @Path("id") repositoryId: Int
    ): Call<RepositoryDetails>

    @GET("users/{login}")
    fun getUsersDetails(
        @Path("login") usersLogin: String
    ): Call<UserDetails>

    @GET ("repositories")
    suspend fun getRepositories(
    ): List<Repository>
}