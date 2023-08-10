package com.app.seafoodapp.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.seafoodapp.R


class ToolsFragment : Fragment() {
    private var toolsViewModel: ToolsViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        toolsViewModel = ViewModelProviders.of(this).get<ToolsViewModel>(ToolsViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_tools, container, false)
        val textView: TextView = root.findViewById<TextView>(R.id.text_tools)
        toolsViewModel!!.text.observe(viewLifecycleOwner) { s -> textView.setText(s) }
        return root
    }
}