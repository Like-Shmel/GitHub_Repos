package com.karaev.githubrepos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.karaev.githubrepos.fragments.RepositoriesListFragment

//Главный экран приложения
class MainActivity : AppCompatActivity() {
    // Вызывется при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Был вызван onStart")
        setContentView(R.layout.activity_main)
        navigateRepositoriesFragment()

    }

    private fun navigateRepositoriesFragment() {
        //Выполняем переход на фрагмент списка репозитория
        val repositoriesFragment = RepositoriesListFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, repositoriesFragment)
        transaction.commit()
    }

    // Вызывается при уничтожении экрана
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "Был вызван onDestroy")
    }
// Вызывается при запуске приложения
    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "Был вызван onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "Был вызван onStop")
    }
}

