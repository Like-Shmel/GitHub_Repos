package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.*
import com.karaev.githubrepos.databinding.FragmentDetailsRepositoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsFragment : Fragment(R.layout.fragment_details_repository) {
    private var binding: FragmentDetailsRepositoryBinding? = null

    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsRepositoryBinding.bind(view)

        binding!!.toolBarDetails.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
//                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
//                fragmentManager.popBackStack()
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.toolBarDetails.setOnMenuItemClickListener(object :
            Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item != null) {
                    if (item.itemId == R.id.menu_share) {
                         GitHubReposApplication.router.navigateTo(Screens.share(repositoryDetails!!.link))
                    }
                }
                return true
            }
        })


        binding!!.openDetailsUser.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                GitHubReposApplication.router.navigateTo(Screens.profileInfo(repositoryDetails!!.owner.login))
            }
        })


        val repository: java.io.Serializable = requireArguments().getSerializable(ARGUMENTS_ID)!!
        val repositoryParam = repository as Repository

        val getRepositoriesDetails =
            GitHubReposApplication.gitHubService.getRepositoriesDetails(repositoryParam.id)


        getRepositoriesDetails.enqueue(object : Callback<RepositoryDetails> {
            override fun onResponse(
                call: Call<RepositoryDetails>, response: Response<RepositoryDetails>
            ) {
                repositoryDetails = response.body()
                if (repositoryDetails !== null) {

                    Glide.with(this@RepositoryDetailsFragment)
                        .load(repositoryDetails!!.owner.avatar).into(binding!!.avatarImageview)

                    binding!!.infoLoginTextview.setText(repositoryDetails!!.name)

                    binding!!.infoNameTextview.setText((repositoryDetails!!.owner.login))

                    binding!!.infoDescriptionTextview.setText(repositoryDetails!!.description)

                    binding!!.urlTextview.setText(repositoryDetails!!.link)

                    binding!!.starTextview.setText(repositoryDetails!!.star)

                    binding!!.repostTextview.setText(repositoryDetails!!.repost)

                    binding!!.errorsTextview.setText(repositoryDetails!!.error)

                    binding!!.toolBarDetails.setTitle(repositoryDetails!!.name)

//                    toolBarDetails.setSupportActionBar().setSubtitle("subtitle")

                }
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {

                val snackBar: Snackbar = Snackbar.make(
                    requireView(), t.message!!, Snackbar.LENGTH_LONG
                )
                snackBar.show()
            }
        })
    }

    companion object {

        const val ARGUMENTS_ID = "id"
        fun createFragment(repository: Repository): Fragment {
            val reposList = RepositoryDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENTS_ID, repository)
            reposList.arguments = bundle
            return reposList
        }
    }


}