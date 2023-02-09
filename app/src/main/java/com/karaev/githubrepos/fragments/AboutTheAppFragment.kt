package com.karaev.githubrepos.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.karaev.githubrepos.R

class AboutTheAppFragment: Fragment(R.layout.fragment_about_the_application) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolBar: Toolbar = view.findViewById(R.id.about_app_toolbar)
        toolBar.setNavigationOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }

        })

    }
}