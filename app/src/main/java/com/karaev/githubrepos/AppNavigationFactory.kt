package com.karaev.githubrepos

import com.karaev.githubrepos.fragments.*
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory

class AppNavigationFactory: RegistryNavigationFactory() {

    init {
        registerFragment(FeaturedAuthorsScreen::class.java, FeaturedAuthorsFragment::class.java)
        registerFragment(AboutTheAppScreen::class.java, AboutTheAppFragment::class.java)
        registerFragment(SettingsScreen::class.java, SettingsFragment::class.java)
        registerFragment(RepositoriesListScreen::class.java, RepositoriesListFragment::class.java)
        registerFragment(RepositoryDetailsScreen::class.java, RepositoryDetailsFragment::class.java)
        registerFragment(UserDetailsFragmentScreen::class.java, UserDetailsFragment::class.java)


    }

}