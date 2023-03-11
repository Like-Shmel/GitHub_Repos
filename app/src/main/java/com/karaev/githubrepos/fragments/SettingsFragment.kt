package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.databinding.FragmentSettingsBinding
import com.karaev.githubrepos.viewModels.SettingsViewModel

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null

    private val ViewModel: SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding!!.toolBarSettings.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.saveButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val settingsToast: Toast = Toast.makeText(
                    requireActivity(),
                    "Настройки сохранены",
                    Toast.LENGTH_SHORT
                )
                settingsToast.show()
                val repositoriesList = binding!!.numberOfRepositoriesEditText.text.toString()
                ViewModel.saveSettings(repositoriesList)

            }
        })

        ViewModel.setNumber()

        ViewModel.numberOfRepositoriesLiveData.observe(viewLifecycleOwner){
            binding!!.numberOfRepositoriesEditText.setText(it)
        }

    }


}