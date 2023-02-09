package com.karaev.githubrepos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.RepositoryDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsFragment: Fragment(R.layout.fragment_details_repository) {


    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolBarDetails: Toolbar = view.findViewById(R.id.toolBar_details)
        toolBarDetails.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val fragmentManager: FragmentManager =
                    requireActivity().supportFragmentManager
                fragmentManager.popBackStack()

            }

        })

       toolBarDetails.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener{
           override fun onMenuItemClick(item: MenuItem?): Boolean {
               if (item != null) {
                   if(item.itemId == R.id.menu_share){
                    val shareIntent = Intent()
                       shareIntent.setAction(Intent.ACTION_SEND)
                       shareIntent.setType("text/plain")
                       shareIntent.putExtra(Intent.EXTRA_TEXT, repositoryDetails!!.link)
                       requireActivity().startActivity(shareIntent)
                   }
               }
               return true
           }
       })

        val openDetailsRepositoryFragment: LinearLayout = view.findViewById(R.id.open_details_user)
        openDetailsRepositoryFragment.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {



                val userDetailsFragment = UserDetailsFragment()
                val bundle = Bundle()
                bundle.putString("login", repositoryDetails!!.owner.login)
                userDetailsFragment.arguments = bundle

                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.main_fragment_container_view, userDetailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

        })

        val repositoryId: Int = requireArguments().getInt("id",0)


       val getRepositoriesDetails = GitHubReposApplication.gitHubService.getRepositoriesDetails(repositoryId)

        getRepositoriesDetails.enqueue(object : Callback<RepositoryDetails> {
            override fun onResponse(
                call: Call<RepositoryDetails>,
                response: Response<RepositoryDetails>
            ) {
                repositoryDetails = response.body()
                if(repositoryDetails !== null){

                    val avatar : ImageView = view.findViewById(R.id.avatar_imageview)
                    Glide.with(this@RepositoryDetailsFragment)
                        .load(repositoryDetails!!.owner.avatar)
                        .into(avatar)

                    val login: TextView = view.findViewById(R.id.info_login_textview)
                    login.setText(repositoryDetails!!.name)

                    val name: TextView = view.findViewById(R.id.info_name_textview)
                    name.setText((repositoryDetails!!.owner.login))

                    val description: TextView = view.findViewById(R.id.info_description_textview)
                    description.setText(repositoryDetails!!.description)

                    val url : TextView = view.findViewById(R.id.url_textview)
                    url.setText(repositoryDetails!!.link)

                    val star : TextView = view.findViewById(R.id.star_textview)
                    star.setText(repositoryDetails!!.star)

                    val repost: TextView = view.findViewById(R.id.repost_textview)
                    repost.setText(repositoryDetails!!.repost)

                    val error: TextView = view.findViewById(R.id.errors_textview)
                    error.setText(repositoryDetails!!.error)

                    val toolBarName: Toolbar = view.findViewById(R.id.toolBar_details)
                    toolBarName.setTitle(repositoryDetails!!.name)

//                    toolBarDetails.setSupportActionBar().setSubtitle("subtitle")

                }
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {

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