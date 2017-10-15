package com.example.uemotoakira.application14

import android.Manifest
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.location.LocationManager
import android.widget.Toast
import android.content.pm.PackageManager
import android.content.Context
import android.support.v4.content.ContextCompat
import android.icu.text.SimpleDateFormat
import android.location.Location
import java.util.*
import android.location.LocationListener




class MainActivity : AppCompatActivity() {

    private val MIN_TIME = (1000 * 10).toLong() // 更新時間の最小値（10秒）
    private val MIN_DISTANCE = 10.0f // 更新距離の最小値（10メートル）
    private val PERMISSION_ERROR_MSG = "Locationのパーミッションがありません"

    private lateinit var textViewTime: TextView // 測定時刻
    private lateinit var textViewLon: TextView // 経度
    private lateinit var textViewLat: TextView // 緯度
    private lateinit var textViewAlt: TextView  // 高度（オプション）
    private lateinit var textViewAcc: TextView // 精度（オプション）

    private lateinit var locationManager: LocationManager
    private lateinit var myLocationListener: MyLocationListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewTime =  findViewById<TextView>(R.id.textViewTime);
        textViewLon = findViewById<TextView>(R.id.textViewLongitude)
        textViewLat = findViewById<TextView>(R.id.textViewLatitude)
        textViewAlt = findViewById<TextView>(R.id.textViewAltitude)
        textViewAcc = findViewById<TextView>(R.id.textViewAccuracy)

        val myLocationListener = MyLocationListener()
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()

        // パーミッションのチェック
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // エラーメッセージ表示
            Toast.makeText(this, PERMISSION_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } else {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            // GPSプロバイダーが使えるかどうかチェック
            if (null != locationManager.getProvider(LocationManager.GPS_PROVIDER)) {
                // 最後に計測されたLocationの情報をとりあえず表示する
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                setLocationText(location)

                // LocationManagerにLocationListenerを登録する
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, myLocationListener)
            }
        }
    }

    override fun onPause() {
        // パーミッションのチェック
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // エラーメッセージ表示
            Toast.makeText(this, PERMISSION_ERROR_MSG, Toast.LENGTH_SHORT).show()
        } else {
            locationManager.removeUpdates(myLocationListener)
        }

        super.onPause()
    }

    /**
     * 位置情報を表示する
     */
    private fun setLocationText(location: Location?) {
        if (location != null) {
            val time = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date(location!!.getTime()))
            val lon = location!!.getLongitude()
            val lat = location!!.getLatitude()
            textViewTime.text = "測定時刻：" + time
            textViewLon.text = "経度：" + lon
            textViewLat.text = "緯度：" + lat

            if (location!!.hasAccuracy()) {
                val acc = location!!.getAccuracy()
                textViewAcc.text = "精度：" + acc
            }
            if (location!!.hasAltitude()) {
                val alt = location!!.getAltitude()
                textViewAlt.text = "高度：" + alt
            }
        }
    }

    /**
     * LocationListener
     */
    internal inner class MyLocationListener : LocationListener {

        override fun onLocationChanged(location: Location) {
            // 位置に変化があった場合に画面に表示する
            setLocationText(location)
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

        override fun onProviderEnabled(s: String) {}

        override fun onProviderDisabled(s: String) {}
    }
}
