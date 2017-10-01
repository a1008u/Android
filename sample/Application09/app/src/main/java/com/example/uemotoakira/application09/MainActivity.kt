package com.example.uemotoakira.application09

import android.app.AlarmManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.PendingIntent
import android.content.Intent
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val WAIT_MILLISEC = (10 * 1000).toLong()
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        /**
         * getActivity:::Activityを実行するためのPendingIntentを取得する
         */
        findViewById<Button>(R.id.button1).setOnClickListener {
            val pendingIntent1: PendingIntent = Intent(this, SubActivity::class.java).let {
                                                    PendingIntent.getActivity(this, 0, it, 0)
                                                }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + WAIT_MILLISEC, pendingIntent1)
        }

        /**
         * getBroadcast:::BroadcastReciverを実行するためのPendingIntentを取得する
         */
        findViewById<Button>(R.id.button2).setOnClickListener {
            val pendingIntent2: PendingIntent = Intent(this, BcastReceiver::class.java).let {
                                                    PendingIntent.getBroadcast(this, 0, it, 0)
                                                }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + WAIT_MILLISEC, pendingIntent2)
        }

    }
}
