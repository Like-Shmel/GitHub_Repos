package com.karaev.githubrepos

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.karaev.githubrepos.fragments.*

object Screens {

    fun authors() = FragmentScreen { FeaturedAuthorsFragment() }
    fun app() = FragmentScreen { AboutTheAppFragment() }
    fun settings() = FragmentScreen { SettingsFragment() }
    fun repositories() = FragmentScreen { RepositoriesListFragment() }

    //    fun main() = ActivityScreen { MainActivity.createIntent(it)}
    fun reposList(repository: Repository) =
        FragmentScreen { RepositoryDetailsFragment.createFragment(repository = repository) }

    fun profileInfo(login: String) =
        FragmentScreen { UserDetailsFragment.createFragment(login) }


// Внешние экраны

    fun share(link : String)  = ActivityScreen { val shareIntent = Intent()
        shareIntent.setAction(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        shareIntent
    }
}