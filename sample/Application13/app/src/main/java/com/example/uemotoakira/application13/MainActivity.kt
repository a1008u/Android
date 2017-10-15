package com.example.uemotoakira.application13

import android.hardware.Sensor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.hardware.SensorManager
import android.widget.Toast
import android.hardware.SensorEvent
import android.hardware.SensorEventListener


class MainActivity : AppCompatActivity() {

    lateinit var sensorManager: SensorManager // センサーマネージャー
    lateinit var accelSensor: Sensor // 加速度センサー
    lateinit var mySensorEventListener: MySensorEventListener // センサーのイベントリスナー

    lateinit var textViewX : TextView  // X軸方向の加速度表示用
    lateinit var textViewY : TextView // Y軸方向の加速度表示用
    lateinit var textViewZ : TextView // Z軸方向の加速度表示用

    val UPDATE_INTERVAL = 1000 // 表示の変更間隔（ミリ秒）
    var lastUpdate: Long = 0

    /**
     * getDefaultSensor(type :Int) :Sensor
     *  センサーのタイプを指定してSensorオブジェクトを取得します。
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewX = findViewById<TextView>(R.id.textViewX)
        textViewY = findViewById<TextView>(R.id.textViewY)
        textViewZ = findViewById<TextView>(R.id.textViewZ)

        // SensorManagerを取得する
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // 加速度センサーを取得する
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelSensor == null)
            Toast.makeText(this, "加速度センサーが使えません", Toast.LENGTH_SHORT).show()
    }

    /**
     * registerListener(listener : SensorEventListener, sensor: Sensor, samplingPeriodUs)
     *  センサーに対してイベントリスナーとサンプリングの頻度を設定
     *  センサーが受け取ったイベントは指定されたサンプリング頻度でイベントリスナーに渡されます。
     *  センサーが利用可能の場合trueを、それ以外の場合falseを返します。
     *   listener::イベントリスナーを指定
     *   sensor::イベントリスナーを設定したいセンサーを指定
     *   samplingPeriodUs::サンプリングを行うおよその頻度を指定
     */
    override fun onResume() {
        super.onResume()
        // センサーのイベントリスナーをSensorManagerに登録する
        mySensorEventListener = MySensorEventListener()
        sensorManager.registerListener(mySensorEventListener, accelSensor, SensorManager.SENSOR_DELAY_UI)
        lastUpdate = System.currentTimeMillis()
    }

    override fun onPause() {
        // リスナーを登録解除する
        sensorManager.unregisterListener(mySensorEventListener)
        super.onPause()
    }

    inner class MySensorEventListener : SensorEventListener {

        /**
         * センサーの値に変化があった場合に実行
         */
        override fun onSensorChanged(sensorEvent: SensorEvent) {
            if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                // センサーのタイプが加速度センサーの場合の処理
                System.currentTimeMillis().let {
                    if (it - lastUpdate > UPDATE_INTERVAL) {
                        lastUpdate = it

                        // センサーの値を取得 + センサーの値を表示する
                        textViewX.text = sensorEvent.values[0].toString() // X
                        textViewY.text = sensorEvent.values[1].toString() // Y
                        textViewZ.text = sensorEvent.values[2].toString() // Z
                    }
                }

            }
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }
}
