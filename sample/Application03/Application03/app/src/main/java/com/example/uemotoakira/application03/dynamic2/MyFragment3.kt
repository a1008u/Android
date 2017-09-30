package com.example.uemotoakira.application03.dynamic2

import android.app.ListFragment
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.uemotoakira.application03.R
import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter

class MyFragment3 : ListFragment() {

    private var listener: ListSelectionListener? = null

    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater?
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     *
     */
    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        Toast.makeText(activity, "position=" + position, Toast.LENGTH_SHORT).show()
        listener?.onListSelection(position)
    }

    /**
     *
     */
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        listener = activity as ListSelectionListener
    }

    /**
     *
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ListSelectionListener
    }

    /**
     *
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var titleArray: Array<String>? = resources.getStringArray(R.array.Titles)
        listAdapter = ArrayAdapter<String>(activity, R.layout.fragment_my3, titleArray)
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
    }

}