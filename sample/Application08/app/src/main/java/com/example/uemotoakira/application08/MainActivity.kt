package com.example.uemotoakira.application08

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.IntentFilter



class MainActivity : AppCompatActivity() {

    lateinit var myReceiver:MyBcastReceiver
    lateinit var filter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myReceiver = MyBcastReceiver()
        filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
    }

    override fun onResume() {
        super.onResume()
        // BroadcastReceiverとfilterを登録する
        registerReceiver(myReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        // BroadcastReceiverを登録から削除する
        if (myReceiver != null) this.unregisterReceiver(myReceiver)
    }
}
