package com.karaev.githubrepos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.Repository
import com.karaev.githubrepos.RepositoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Экран для отображения списка репозитория
class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

    val openFeaturedAuthorsFragment = FeaturedAuthorsFragment()
    val settingsFragment = SettingsFragment()
    private var repositoryAdapter =
        RepositoryAdapter(repositoryListener = object : RepositoryAdapter.RepositoryListener {
            override fun onItemClick(repository: Repository) {

                val repositoryDetailsFragment = RepositoryDetailsFragment()

                val bundle = Bundle()
                bundle.putInt("id", repository.id)
                repositoryDetailsFragment.arguments = bundle
                navigateFragment(repositoryDetailsFragment)
            }


        })
    lateinit var getUsersCall: Call<List<Repository>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repositoryRecyclerView: RecyclerView = view.findViewById(R.id.repository_recyclerview)

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        repositoryRecyclerView.addItemDecoration(divider)

        repositoryRecyclerView.adapter = repositoryAdapter
        loadRepositoriesList()


        val spinner: Spinner = view.findViewById(R.id.spinner_repos_list)
        spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position == 0) {
                    repositoryAdapter.repositories.sortBy { it.id }

                } else if (position == 1) {
                    repositoryAdapter.repositories.sortBy { it.name }
                }
                repositoryAdapter.notifyDataSetChanged()

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })


        val featuredAuthors: Toolbar = view.findViewById(R.id.toolBar_favorites)
        featuredAuthors.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (item.itemId == R.id.menu_favorites) {
                    openFeaturedFragment(openFeaturedAuthorsFragment)
                } else if (item.itemId == R.id.menu_about_app) {
                    openAboutAppFragment()
                } else if (item.itemId == R.id.setting_favorites) {
                    openSettingsFragment(settingsFragment)
                }
                return true
            }

        })


    }

    private fun openAboutAppFragment() {
        val aboutApp = AboutTheAppFragment()
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, aboutApp)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun openFeaturedFragment(fragment: Fragment) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun navigateFragment(fragment: Fragment) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openSettingsFragment(fragment: Fragment) {
        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container_view, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun loadRepositoriesList() {
        val reposListProgressBar: ProgressBar =
            requireView().findViewById(R.id.repos_list_progressBar)
        reposListProgressBar.visibility = View.VISIBLE
        getUsersCall = GitHubReposApplication.gitHubService.getRepositories()

        getUsersCall.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>, response: Response<List<Repository>>
            ) {
                reposListProgressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val usersList: List<Repository> = response.body()!!

                    // Обратиться к хранилищу со значениями

                    val sharedPreferences: SharedPreferences =
                        requireContext().getSharedPreferences(
                            "git_hub_preferences", Context.MODE_PRIVATE
                        )

                    // Получить значение по ключу

                    val repositoriesEditor: String? =
                        sharedPreferences.getString("repositoriesNumber", null)

                    //Установить список с новым значение

//                    var repositoriesNum = 0
//
//                    if (repositoriesEditor != ""){
//                        repositoriesNum = repositoriesEditor
//                    }else{
//                        repositoriesNum = usersList.size
//                    }

                    var repositoriesList = usersList.take(repositoriesEditor?.toInt() ?: 20)
                    repositoryAdapter.repositories = repositoriesList.toMutableList()
                    repositoryAdapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                reposListProgressBar.visibility = View.GONE

                val errorSnackBar = Snackbar.make(
                    requireView(), t.message!!, Snackbar.LENGTH_LONG
                )
                errorSnackBar.show()
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        getUsersCall.cancel()
    }


}



