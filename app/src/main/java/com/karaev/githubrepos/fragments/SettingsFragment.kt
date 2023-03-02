package com.karaev.githubrepos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.karaev.githubrepos.GitHubReposApplication
import com.karaev.githubrepos.R
import com.karaev.githubrepos.RepositoriesListScreen
import com.karaev.githubrepos.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null
//    lateinit var numberOfRepositoriesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding!!.toolBarSettings.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
//                requireActivity().supportFragmentManager.popBackStack()
                GitHubReposApplication.navigator.replace(RepositoriesListScreen())

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
                saveSettings()
            }

        })

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )


        val numberOfRepositories: String? = sharedPreferences.getString("repositoriesNumber", null)
        binding!!.numberOfRepositoriesEditText.setText(numberOfRepositories)

    }
    private fun saveSettings() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )

            val repositoriesList = binding!!.numberOfRepositoriesEditText.text.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositoriesNumber", repositoriesList)
        editor.apply()
    }

}