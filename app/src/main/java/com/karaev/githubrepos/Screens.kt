package com.karaev.githubrepos

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.karaev.githubrepos.fragments.FeaturedAuthorsFragment

object Screens {
fun authors() = FragmentScreen { FeaturedAuthorsFragment() }
}