package com.example.uemotoakira.myapplication03_1

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


class MyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button = activity.findViewById<Button>(R.id.button)

        val editText1 = activity.findViewById<EditText>(R.id.editText1)

        button.setOnClickListener({ editText1.setText("Button Clicked!") })
    }
}// Required empty public constructor
