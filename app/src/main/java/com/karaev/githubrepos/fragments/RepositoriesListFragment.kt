package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.*
import com.karaev.githubrepos.databinding.FragmentRepositoriesListBinding
import com.karaev.githubrepos.viewModels.RepositoriesListViewModel

// Экран для отображения списка репозитория
class RepositoriesListFragment : Fragment(R.layout.fragment_repositories_list) {

    private val viewModel: RepositoriesListViewModel by lazy {
        ViewModelProvider(this,).get(RepositoriesListViewModel::class.java)
    }
    private var binding: FragmentRepositoriesListBinding? = null

    private var repositoryAdapter =
        RepositoryAdapter(repositoryListener = object : RepositoryAdapter.RepositoryListener {
            override fun onItemClick(repository: Repository) {
                GitHubReposApplication.router.navigateTo(Screens.reposList(repository))
            }
        })

//    lateinit var getUsersCall: Call<List<Repository>>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesListBinding.bind(view)

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.repositoryRecyclerview.addItemDecoration(divider)

        binding!!.repositoryRecyclerview.adapter = repositoryAdapter


        viewModel.loadRepositoriesList()

        binding!!.spinnerReposList.setOnItemSelectedListener(object : OnItemSelectedListener {
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


        binding!!.toolBarFavorites.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (item.itemId == R.id.menu_favorites) {
                    GitHubReposApplication.router.navigateTo(Screens.authors())
                } else if (item.itemId == R.id.menu_about_app) {
                    GitHubReposApplication.router.navigateTo(Screens.app())
                } else if (item.itemId == R.id.setting_favorites) {
                    GitHubReposApplication.router.navigateTo(Screens.settings())
                }
                return true
            }
        })

        viewModel.repositoryListLiveData.observe(viewLifecycleOwner) {
            repositoryAdapter.repositories = it.toMutableList()
            repositoryAdapter.notifyDataSetChanged()
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner){
            if (it !== true) {
                binding!!.reposListProgressBar.visibility = View.VISIBLE
            } else {
                binding!!.reposListProgressBar.visibility = View.GONE
            }
        }


        viewModel.errorLiveData.observe(viewLifecycleOwner){ t ->
//            binding!!.reposListProgressBar.visibility = View.GONE
                val errorSnackBar = Snackbar.make(
                    requireView(), t.message!!, Snackbar.LENGTH_LONG
                )
                errorSnackBar.show()
        }



    }






}



