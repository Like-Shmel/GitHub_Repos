package com.karaev.githubrepos

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.karaev.githubrepos.databinding.ActivityMainBinding
import me.aartikov.alligator.NavigationContext

//Главный экран приложения
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // Вызывется при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Был вызван onStart")
        setContentView(R.layout.activity_main)
        navigateRepositoriesFragment()

    }

    private fun navigateRepositoriesFragment() {
        //Выполняем переход на фрагмент списка репозитория
        GitHubReposApplication.navigator.replace(RepositoriesListScreen())
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

    override fun onResume() {
        super.onResume()
        val navigationContext: NavigationContext = NavigationContext.Builder(this, GitHubReposApplication.navigatorFactory)
            .fragmentNavigation(supportFragmentManager, R.id.main_fragment_container_view)
            .build()
        GitHubReposApplication.navigatorContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        super.onPause()
        GitHubReposApplication.navigatorContextBinder.unbind(this)
    }

    override fun onBackPressed() {
        GitHubReposApplication.navigator.goBack()
    }
}

