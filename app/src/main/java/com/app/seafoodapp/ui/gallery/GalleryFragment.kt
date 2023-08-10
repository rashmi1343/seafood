package com.app.seafoodapp.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.seafoodapp.R


class GalleryFragment : Fragment() {
    private var galleryViewModel: GalleryViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get<GalleryViewModel>(GalleryViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_gallery, container, false)
        val textView: TextView = root.findViewById<TextView>(R.id.text_gallery)
        galleryViewModel!!.text.observe(viewLifecycleOwner) { s -> textView.setText(s) }
        return root
    }
}