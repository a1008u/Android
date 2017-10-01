package com.example.uemotoakira.application07

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator

class SubMainActivity : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_main)

        var vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(1000)
    }
}
