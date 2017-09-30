package com.example.uemotoakira.application03

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


class MyFragment : Fragment() {

    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_my, container, false)
    }

    /**
     * 静的なFragment
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button = activity.findViewById<View>(R.id.button1) as Button
        val editText1 = activity.findViewById<View>(R.id.editText1) as EditText
        button.setOnClickListener({ editText1.setText("Button Clicked!") })
    }


}
