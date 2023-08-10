package com.app.seafoodapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.seafoodapp.R


class HomeFragment : Fragment() {
    private var homeViewModel: HomeViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get<HomeViewModel>(HomeViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById<TextView>(R.id.text_home)
        homeViewModel!!.text.observe(viewLifecycleOwner) { s -> textView.setText(s) }
        return root
    }
}