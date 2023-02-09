package com.karaev.githubrepos

import android.app.Application
import androidx.room.Room
import database.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GitHubReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "gitRepos_database"
        )
            .allowMainThreadQueries()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gitHubService = retrofit.create()
    }

    companion object {
        lateinit var gitHubService: GitHubService

        lateinit var appDatabase: AppDatabase
    }

}