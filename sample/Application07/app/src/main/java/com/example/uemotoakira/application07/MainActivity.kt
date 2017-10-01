package com.example.uemotoakira.application07

import android.app.Notification
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.app.PendingIntent
import android.app.NotificationManager
import android.content.Context
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private val MY_NOTIFICATION_AREAID = 1

    /**
     * PendingIntent
     * 　・Activityで作成されたIntentの情報を別の場所に保存する
     * 　・他のActivityの実行にパーミッションが必要な場合でも、PendingIntentを使うことにより実行元のActivityと同じ権限で実行することができる
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val builder = Notification.Builder(this@MainActivity)
                                    .setTicker("このTickerは表示されない場合もあります(API21まで)")
                                    .setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND)
                                    .setAutoCancel(true)
                                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                                    .setContentTitle("これはNotificationのタイトルです")
                                    .setContentText("これはNotificationのテキストです")

        val intent = Intent(this, SubMainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        findViewById<Button>(R.id.button).setOnClickListener({
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(MY_NOTIFICATION_AREAID, builder.build())
        })
    }
}
