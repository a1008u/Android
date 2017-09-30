package com.example.uemotoakira.application03.dynamic2

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uemotoakira.application03.R
import android.widget.TextView

class MyFragment4 : Fragment() {

    private var textView: TextView? = null

    /**
     * リストの項目を押下時に起動する。要求元はMain3Activityです。
     */
    fun setContentAtIndex(newIndex: Int) {
        var contentArray: Array<String> = resources.getStringArray(R.array.Contents)
        textView?.text = contentArray[newIndex]
    }

    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my4, container, false)
    }

    /**
     *
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        textView = activity.findViewById(R.id.fragment4_textView)
    }


}