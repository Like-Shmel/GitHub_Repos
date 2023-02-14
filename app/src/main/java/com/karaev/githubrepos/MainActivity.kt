package com.karaev.githubrepos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.karaev.githubrepos.fragments.RepositoriesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","Был вызван onStart")
        setContentView(R.layout.activity_main)
        navigateRepositoriesFragment()

    }

    private fun navigateRepositoriesFragment(){
        val repositoriesFragment = RepositoriesListFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, repositoriesFragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity","Был вызван onDestroy")
    }

    override fun onStart(){
        super.onStart()
        Log.d("MainActivity","Был вызван onStart")
    }

    override fun onStop(){
        super.onStop()
        Log.d("MainActivity","Был вызван onStop")
    }
}

