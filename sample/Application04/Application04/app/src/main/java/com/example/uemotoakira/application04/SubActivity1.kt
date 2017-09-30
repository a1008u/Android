package com.example.uemotoakira.application04

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 * 明示的Intent(値をやり取りしない場合)
 */
class SubActivity1 : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub1)
    }
}
