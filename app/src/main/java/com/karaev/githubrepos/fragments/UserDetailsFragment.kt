package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.UserDetails
import com.karaev.githubrepos.databinding.FragmentDetailsUserBinding
import com.karaev.githubrepos.viewModels.UserDetailsViewModel
import database.FavoritesDao
import models.Favorites

class UserDetailsFragment : Fragment(R.layout.fragment_details_user) {
    private var binding: FragmentDetailsUserBinding? = null

    val viewModel: UserDetailsViewModel by lazy {
        ViewModelProvider(this).get(UserDetailsViewModel::class.java)
    }
    var userDetails: UserDetails? = null

    val favoritesDao: FavoritesDao = GitHubReposApplication.appDatabase.favoritesDao()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsUserBinding.bind(view)

        binding!!.toolBarUserLogin.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val fragmentManager: FragmentManager =
                    requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }
        })

        lateinit var nameFavorites: String
        lateinit var loginFavorites: String
        lateinit var avatarFavorites: String

        binding!!.toolBarUserLogin.setOnMenuItemClickListener(object :
            Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {

                if (item?.itemId == R.id.menu_add_favorites) {

                    val newFavorites = Favorites(
                        name = nameFavorites,
                        login = loginFavorites,
                        avatar = avatarFavorites
                    )
                    favoritesDao.insertFavorites(newFavorites)

                    val favoriteToast: Toast = Toast.makeText(
                        requireActivity(),
                        "Добавлен в Избранные авторы",
                        Toast.LENGTH_SHORT
                    )
                    favoriteToast.show()
                }
                return true
            }
        })


        val usersLogin: String = requireArguments().getString(ARGUMENTS_LOGIN, "0")

        viewModel.userDetailsLiveData.observe(viewLifecycleOwner, object : Observer<UserDetails> {
            override fun onChanged(t: UserDetails) {
                userDetails = t

                nameFavorites = userDetails!!.name
                loginFavorites = userDetails!!.login
                avatarFavorites = userDetails!!.avatar

                Glide.with(this@UserDetailsFragment)
                    .load(userDetails!!.avatar)
                    .into(binding!!.avatarUserImageview)

                binding!!.nameUserTextview.setText(t.name)

                binding!!.loginUserTextview.setText(t.login)

                binding!!.mailUserTextview.setText(t.email)

                binding!!.followingUserTextview.setText(t.following.toString())

                binding!!.followersUserTextview.setText(t.followers.toString())

                binding!!.repositoriesUserTextview.setText(t.public_repos.toString())

                binding!!.loginUserTextview.setText(t.location)

                binding!!.bioUsersTextview.setText(t.bio)

                binding!!.toolBarUserLogin.setTitle(t.login)

            }

        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, object : Observer<String> {
            override fun onChanged(t: String) {
                val snackBar: Snackbar = Snackbar.make(
                    requireView(),
                    t,
                    Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })

        viewModel.loadUserDetails(usersLogin)
    }

    companion object {
        const val ARGUMENTS_LOGIN = "login"
        fun createFragment(login: String): UserDetailsFragment {
            val reposList = UserDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_LOGIN, login)
            reposList.arguments = bundle
            return reposList
        }
    }
}