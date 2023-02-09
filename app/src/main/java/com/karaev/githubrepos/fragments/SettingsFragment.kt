package com.karaev.githubrepos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.karaev.githubrepos.R

class SettingsFragment: Fragment(R.layout.fragment_settings) {

    lateinit var numberOfRepositoriesEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.toolBar_settings)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()

                val settingsToast: Toast = Toast.makeText(
                    requireActivity(),
                    "Настройки сохранены",
                    Toast.LENGTH_SHORT
                )
                settingsToast.show()
            }
        })


        val saveButton: Button = view.findViewById(R.id.save_button)
        saveButton.setOnClickListener(object : View.OnClickListener{
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
        numberOfRepositoriesEditText = view.findViewById(R.id.number_of_repositories_editText)

        val numberOfRepositories: String? = sharedPreferences.getString("repositoriesNumber", null)
        numberOfRepositoriesEditText.setText(numberOfRepositories)

    }
    private fun saveSettings() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )

            val repositoriesList = numberOfRepositoriesEditText.text.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositoriesNumber", repositoriesList)
        editor.apply()
    }

}