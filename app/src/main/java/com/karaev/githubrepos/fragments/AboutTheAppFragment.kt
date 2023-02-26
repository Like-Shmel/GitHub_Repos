package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.karaev.githubrepos.R
import com.karaev.githubrepos.databinding.FragmentAboutTheApplicationBinding

class AboutTheAppFragment: Fragment(R.layout.fragment_about_the_application) {

    private var binding: FragmentAboutTheApplicationBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutTheApplicationBinding.bind(view)

        binding!!.aboutAppToolbar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }

        })

    }
}