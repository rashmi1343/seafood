package com.app.seafoodapp.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.seafoodapp.R


class SendFragment : Fragment() {
    private var sendViewModel: SendViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        sendViewModel = ViewModelProviders.of(this).get<SendViewModel>(SendViewModel::class.java)
        val root: View = inflater.inflate(R.layout.fragment_send, container, false)
        val textView: TextView = root.findViewById<TextView>(R.id.text_send)
        sendViewModel!!.text.observe(viewLifecycleOwner) { s -> textView.setText(s) }
        return root
    }
}