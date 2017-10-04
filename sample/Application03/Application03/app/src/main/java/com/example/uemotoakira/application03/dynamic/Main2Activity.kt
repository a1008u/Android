package com.example.uemotoakira.application03.dynamic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.uemotoakira.application03.MyFragment
import com.example.uemotoakira.application03.R

/**
 * Fragmentをプログラムで動的に作成する
 *
 */
class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // add(追加場所のidを指定, 追加したいFragmentを指定)
        fragmentManager.beginTransaction().run { add(R.id.fragment2, My2Fragment())
                                                 commit()
                                               }

    }
}
