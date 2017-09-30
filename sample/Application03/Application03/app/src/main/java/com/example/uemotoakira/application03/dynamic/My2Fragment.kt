package com.example.uemotoakira.application03.dynamic

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.uemotoakira.application03.R


class My2Fragment : Fragment() {

    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my2, container, false)
    }

    /**
     *
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button = activity.findViewById<Button>(R.id.button2)
        val editText1 = activity.findViewById<EditText>(R.id.editText2)
        button.setOnClickListener({ editText1.setText("Button Clicked!") })
    }
}
