package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.UserDetails
import com.karaev.githubrepos.databinding.FragmentDetailsUserBinding
import database.FavoritesDao
import models.Favorites
import retrofit2.Call
import retrofit2.Response

class UserDetailsFragment : Fragment(R.layout.fragment_details_user) {
    private var binding: FragmentDetailsUserBinding? = null
    val favoritesDao: FavoritesDao = GitHubReposApplication.appDatabase.favoritesDao()
    lateinit var getUsersDetails: Call<UserDetails>

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

        binding!!.toolBarUserLogin.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
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
        getUsersDetails = GitHubReposApplication.gitHubService.getUsersDetails(usersLogin)

        getUsersDetails.enqueue(object : retrofit2.Callback<UserDetails> {
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                val userDetails: UserDetails? = response.body()

                nameFavorites = userDetails!!.name
                loginFavorites = userDetails!!.login
                avatarFavorites = userDetails!!.avatar

                if (userDetails !== null) {

                    Glide.with(this@UserDetailsFragment)
                        .load(userDetails!!.avatar)
                        .into(binding!!.avatarUserImageview)

                    binding!!.nameUserTextview.setText(userDetails.name)

                    binding!!.loginUserTextview.setText(userDetails.login)

                    binding!!.mailUserTextview.setText(userDetails.email)

                    binding!!.followingUserTextview.setText(userDetails.following.toString())

                    binding!!.followersUserTextview.setText(userDetails.followers.toString())

                    binding!!.repositoriesUserTextview.setText(userDetails.public_repos.toString())

                    binding!!.loginUserTextview.setText(userDetails.location)

                    binding!!.bioUsersTextview.setText(userDetails.bio)

                    binding!!.toolBarUserLogin.setTitle(userDetails.login)
                }
            }

            override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                val snackBar: Snackbar = Snackbar.make(
                    requireView(),
                    t.message!!,
                    Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })
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