package com.example.uemotoakira.application09

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by uemotoakira on 2017/10/01.
 */
class BcastReceiver : BroadcastReceiver(){

    val MY_NOTIFICATION_ID : Int  = 1

    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, "BroadcastReceiver実行", Toast.LENGTH_SHORT).show()

        val builder = Notification.Builder(context)
                        .setDefaults(Notification.DEFAULT_VIBRATE or Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle("Hello World!")
                        .setContentText("SubActivityを実行します")

        val resultPendingIntent: PendingIntent = Intent(context, SubActivity::class.java).let {
                                                    PendingIntent.getActivity(context
                                                                                , 0
                                                                                , it
                                                                                , PendingIntent.FLAG_UPDATE_CURRENT)
                                                 }

        builder.setContentIntent(resultPendingIntent)
        val  notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(MY_NOTIFICATION_ID, builder.build())
    }

}