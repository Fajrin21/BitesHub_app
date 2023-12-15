package com.example.biteshub

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.biteshub.addStory


class Postingan : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_postingan, container, false)
        val button: Button = view.findViewById(R.id.btnFloating)

        button.setOnClickListener {
            val intent = Intent (requireActivity(), addStory::class.java)
            startActivity(intent)
        }
        return view
    }
}