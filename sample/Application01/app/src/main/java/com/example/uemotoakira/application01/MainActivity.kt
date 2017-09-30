package com.example.uemotoakira.application01

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private fun myLog(tag: String, msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.d(tag, msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myLog(TAG, "onCreate")

        setContentView(R.layout.activity_main)
    }

    override fun onRestart() {
        super.onRestart()
        myLog(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        myLog(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        myLog(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        myLog(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        myLog(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        myLog(TAG, "onDestroy")
    }
}
