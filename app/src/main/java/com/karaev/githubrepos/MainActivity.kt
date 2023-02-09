package com.karaev.githubrepos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.karaev.githubrepos.fragments.RepositoriesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigateRepositoriesFragment()

    }

    private fun navigateRepositoriesFragment(){
        val repositoriesFragment = RepositoriesListFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, repositoriesFragment)
        transaction.commit()
    }
}

