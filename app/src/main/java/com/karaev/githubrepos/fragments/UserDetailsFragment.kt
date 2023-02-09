package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.UserDetails
import database.FavoritesDao
import models.Favorites
import retrofit2.Call
import retrofit2.Response

class UserDetailsFragment : Fragment(R.layout.fragment_details_user) {

    val favoritesDao: FavoritesDao = GitHubReposApplication.appDatabase.favoritesDao()
    lateinit var getUsersDetails: Call<UserDetails>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarUsersDetails: Toolbar = view.findViewById(R.id.toolBar_user_login)
        toolbarUsersDetails.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val fragmentManager: FragmentManager =
                    requireActivity().supportFragmentManager
                fragmentManager.popBackStack()

            }

        })

        lateinit var nameFavorites: String
        lateinit var loginFavorites: String
        lateinit var avatarFavorites: String

        toolbarUsersDetails.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
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



        val usersLogin: String = requireArguments().getString("login", "0")
        getUsersDetails = GitHubReposApplication.gitHubService.getUsersDetails(usersLogin)

        getUsersDetails.enqueue(object : retrofit2.Callback<UserDetails> {
            override fun onResponse(call: Call<UserDetails>, response: Response<UserDetails>) {
                val userDetails: UserDetails? = response.body()

                nameFavorites = userDetails!!.name
                loginFavorites = userDetails!!.login
                avatarFavorites = userDetails!!.avatar

                if (userDetails !== null) {


                    val avatar: ImageView = view.findViewById(R.id.avatar_user_imageview)
                    Glide.with(this@UserDetailsFragment)
                        .load(userDetails!!.avatar)
                        .into(avatar)

                    val name: TextView = view.findViewById(R.id.name_user_textview)
                    name.setText(userDetails.name)

                    val login: TextView = view.findViewById(R.id.login_user_textview)
                    login.setText(userDetails.login)

                    val mail: TextView = view.findViewById(R.id.mail_user_textview)
                    mail.setText(userDetails.email)

                    val following: TextView = view.findViewById(R.id.following_user_textview)
                    following.setText(userDetails.following.toString())

                    val follower: TextView = view.findViewById(R.id.followers_user_textview)
                    follower.setText(userDetails.followers.toString())

                    val public_repos: TextView = view.findViewById(R.id.repositories_user_textview)
                    public_repos.setText(userDetails.public_repos.toString())

                    val location: TextView = view.findViewById(R.id.location_user_textview)
                    location.setText(userDetails.location)

                    val bio: TextView = view.findViewById(R.id.bio_users_textview)
                    bio.setText(userDetails.bio)

                    val toolbar: Toolbar = view.findViewById(R.id.toolBar_user_login)
                    toolbar.setTitle(userDetails.login)

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

}