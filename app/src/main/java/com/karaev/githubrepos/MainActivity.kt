package com.karaev.githubrepos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.karaev.githubrepos.databinding.ActivityMainBinding

//Главный экран приложения
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var navigator = AppNavigator(this, R.id.main_fragment_container_view)

    // Вызывется при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Был вызван onStart")
        setContentView(R.layout.activity_main)
//        navigateRepositoriesFragment()
        GitHubReposApplication.router.replaceScreen(Screens.repositories())

    }

//    private fun navigateRepositoriesFragment() {
//        //Выполняем переход на фрагмент списка репозитория
//        val repositoriesFragment = RepositoriesListFragment()
//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.main_fragment_container_view, repositoriesFragment)
//        transaction.commit()
//    }

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

    override fun onResume() {
        super.onResume()
        GitHubReposApplication.navigateHolder.setNavigator(navigator)
    }

}

