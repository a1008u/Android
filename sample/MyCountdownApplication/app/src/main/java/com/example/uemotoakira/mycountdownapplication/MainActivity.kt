package com.example.uemotoakira.mycountdownapplication

import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.FloatingActionButton
import android.widget.TextView
import android.media.AudioAttributes
import android.media.AudioManager
import android.os.Build

class MainActivity : AppCompatActivity() {

    lateinit var mTimerText : TextView
    lateinit var mSoundPool: SoundPool
    var mSoundResId: Int = 0

    /**
     * CountDownTimer:::現在カウントダウン中か停止中か表すフラグを表示
     */
    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long)
                : CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false

        /**
         * コンストラクタで指定した間隔で呼び出される
         */
        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000 / 60
            val second = millisUntilFinished / 1000 % 60
            mTimerText.text = String.format("%1d:%2$02d", minute, second)
        }

        /**
         * タイマー終了時に呼び出される
         * play:::サウンドIDを指定してサウンドを再生する
         */
        override fun onFinish() {
            mTimerText.text = "0:00"
            mSoundPool.play(mSoundResId, 1.0f, 1.0f, 0, 0, 1.0f)

        }
    }

    /**
     * SoundPoolクラス:::複数の音を同時に鳴らすことができる
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTimerText = findViewById<TextView>(R.id.timer_text).apply { text = "1:00" }

        findViewById<FloatingActionButton>(R.id.play_stop).run{ setOnClickListener {
                MyCountDownTimer((1 * 60 * 1000).toLong(), 100).let {
                    if (it.isRunning) {
                        it.apply { isRunning = false
                                   cancel()}
                        setImageResource(R.drawable.ic_play_arrow_black_24dp)
                    } else {
                        it.apply { isRunning = true
                                   start()}
                        setImageResource(R.drawable.ic_stop_black_24dp)
                    }
                }

            }
        }
    }

    /**
     * アクティビティが表示されたときにメモリロードする
     * load:::リソースからサウンドファイルを読み込む
     */
    override fun onPostResume() {
        super.onPostResume()
        /* アプリを実行している端末のバージョンを判定
         * Build.VERSION.SDK_INT:::実行通のOSのバージョン番号
         * Build.VERSION_CODES:::今までリリースされた全てのAndroidOSのバージョン番号（定数）
         */
        mSoundPool = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        @Suppress("DEPRECATION")
                        SoundPool(1, AudioManager.STREAM_MUSIC, 0)
                     } else {
                        val audioAttributes = AudioAttributes.Builder()
                                                             .setUsage(AudioAttributes.USAGE_ALARM)
                                                             .build()
                        audioAttributes.let { SoundPool.Builder()
                                                       .setMaxStreams(1)
                                                       .setAudioAttributes(it).build()}
                     }
        mSoundResId = mSoundPool.load(this, R.raw.od, 1)
    }

    /**
     * メモリを開放する
     */
    override fun onPause() {
        super.onPause()
        mSoundPool.release()
    }
}
