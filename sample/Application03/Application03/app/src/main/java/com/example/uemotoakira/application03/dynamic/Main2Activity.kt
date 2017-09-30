package com.example.uemotoakira.application03.dynamic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.uemotoakira.application03.MyFragment
import com.example.uemotoakira.application03.R

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        fragmentManager.beginTransaction().run { add(R.id.fragment2, MyFragment())
                                                 commit()
                                               }

    }
}
