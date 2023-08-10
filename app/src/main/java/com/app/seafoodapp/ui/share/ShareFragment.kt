package com.app.seafoodapp.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.seafoodapp.R


class ShareFragment : Fragment() {
    private var shareViewModel: ShareViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        shareViewModel = ViewModelProviders.of(this).get<ShareViewModel>(ShareViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_share, container, false)
        val textView: TextView = root.findViewById<TextView>(R.id.text_share)
        shareViewModel!!.text.observe(viewLifecycleOwner) { s -> textView.setText(s) }
        return root
    }
}