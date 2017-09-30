package com.example.uemotoakira.application02

import android.app.Activity
import android.app.ListActivity
import android.os.Bundle
import android.view.Menu
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.R.menu
import android.view.MenuInflater
import android.view.MenuItem


class Main2Activity : ListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listView = listView
        val items4 = resources.getStringArray(R.array.items4)
        val adapter = ArrayAdapter<String>(this, R.layout.listview_item, items4)

        listAdapter = adapter;

        // ListViewの項目がクリックされた場合のリスナーを登録する
        listView.onItemClickListener = AdapterView.OnItemClickListener {
            adapterView, view, i, l -> Toast.makeText(applicationContext
                                                        ,adapterView.getItemAtPosition(i).toString() + " Clicked!"
                                                        , Toast.LENGTH_SHORT).show()
            }
    }
}
