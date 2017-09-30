package com.example.uemotoakira.myapplication03_1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = fragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val myFragment1 = MyFragment()
        fragmentTransaction.add(R.id.fragment1, myFragment1);
        fragmentTransaction.commit()
    }
}
