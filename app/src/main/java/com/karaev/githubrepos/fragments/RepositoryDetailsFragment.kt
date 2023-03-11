package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.karaev.githubrepos.*
import com.karaev.githubrepos.databinding.FragmentDetailsRepositoryBinding
import com.karaev.githubrepos.viewModels.RepositoryDetailViewModel

class RepositoryDetailsFragment : Fragment(R.layout.fragment_details_repository) {
    private var binding: FragmentDetailsRepositoryBinding? = null

    val viewModel: RepositoryDetailViewModel by lazy {
        ViewModelProvider(this).get(RepositoryDetailViewModel::class.java)
    }

    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsRepositoryBinding.bind(view)

        binding!!.toolBarDetails.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
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

        viewModel.repositoryDetailsLiveData.observe(
            viewLifecycleOwner,
            object : Observer<RepositoryDetails> {
                override fun onChanged(t: RepositoryDetails) {
                    repositoryDetails = t

                    Glide.with(this@RepositoryDetailsFragment)
                        .load(t.owner.avatar)
                        .into(binding!!.avatarImageview)

                    binding!!.infoLoginTextview.setText(t.name)

                    binding!!.infoNameTextview.setText((t.owner.login))

                    binding!!.infoDescriptionTextview.setText(t.description)

                    binding!!.urlTextview.setText(t.link)

                    binding!!.starTextview.setText(t.star)

                    binding!!.repostTextview.setText(t.repost)

                    binding!!.errorsTextview.setText(t.error)

                    binding!!.toolBarDetails.setTitle(t.name)

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

        viewModel.loadRepositoryDetails(repository = repository.id)

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