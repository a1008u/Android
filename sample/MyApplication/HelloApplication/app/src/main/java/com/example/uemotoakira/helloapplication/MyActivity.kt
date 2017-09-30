package com.example.uemotoakira.helloapplication

import android.app.Activity
import android.os.Bundle
import android.content.Intent



/**
 * Created by uemotoakira on 2017/08/28.
 */
class MyActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v = MyView(this)

        var vx = intent.getFloatExtra("vx", 1F)
        var vy = intent.getFloatExtra("vy", 1F)
        v.mVX = vx
        v.mVY = vy


        setContentView(v)
    }
}