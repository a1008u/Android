package com.example.uemotoakira.application08

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.widget.Toast
import android.net.NetworkInfo
import android.net.ConnectivityManager
import android.content.Intent
import android.util.Log


class MyBcastReceiver : BroadcastReceiver() {

    val TAG = "MyBcastReceivery"


    override fun onReceive(context: Context, intent: Intent) {

        if (isInitialStickyBroadcast) return

        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var networkInfo: NetworkInfo? = null
        var message = "ネットワークが使えません（dynamic）"

        if (connMgr != null) {
            networkInfo = connMgr.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                message = "ネットワークが使えます（dynamic）:" + networkInfo.typeName
            }
        }
        Log.d(TAG, message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
