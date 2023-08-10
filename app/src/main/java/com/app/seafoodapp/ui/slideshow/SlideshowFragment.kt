package com.app.seafoodapp.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.seafoodapp.R


class SlideshowFragment : Fragment() {
    private var slideshowViewModel: SlideshowViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get<SlideshowViewModel>(SlideshowViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById<TextView>(R.id.text_slideshow)
        slideshowViewModel!!.text.observe(viewLifecycleOwner) { s -> textView.setText(s) }
        return root
    }
}