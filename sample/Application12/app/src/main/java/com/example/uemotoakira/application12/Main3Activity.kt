package com.example.uemotoakira.application12

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.SurfaceView
import android.view.SurfaceHolder
import java.io.IOException


class Main3Activity : AppCompatActivity() {

    private val TAG = "MyVideoMediaPlayer01"
    private lateinit var buttonPlay: Button
    private lateinit var mediaPlayer: MediaPlayer
    private val VIDEO_PATH = "android.resource://com.example.uemotoakira.application12/raw/video01"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // MyCallbackを作成し、SurfaceHolderのコールバックとして登録する
        // SurfaceViewの状態の変化に応じてMyCallback内のメソッドが呼び出される
        MyCallback().let {
            val surfaceView = findViewById<SurfaceView>(R.id.surfaceView)
            surfaceView.holder.run { addCallback(it) }
        }

        // Playボタンが押された場合の処理
        buttonPlay = findViewById<Button>(R.id.buttonPlay)
        buttonPlay.run { isEnabled = false
            setOnClickListener { mediaPlayer.start() }
        }

    }

    /**
     * SurfaceHolder.CallbackとMediaPlayer.OnPreparedListenerの実装クラス
     */
    private inner class MyCallback : SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

        /**
         * MediaPlayerの再生の準備ができた場合に実行される
         */
        override fun onPrepared(mediaPlayer: MediaPlayer) {
            // 再生の準備が完了したらボタンを押せるようにする
            buttonPlay.isEnabled = true
        }

        /**
         * SurfaceViewの作成後に実行される
         */
        override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
            mediaPlayer = MediaPlayer()
            mediaPlayer.run { setDisplay(surfaceHolder)
                // 再生データの最後に到達した場合のリスナーを登録
                // 再生が完了した場合の処理
                setOnCompletionListener { mediaPlayer -> mediaPlayer.release() }

                try {
                    // ファイルの場所からUriを作成し、再生の準備を行う
                    setDataSource(applicationContext, Uri.parse(VIDEO_PATH))
                    setOnPreparedListener(this@MyCallback)
                    prepare()
                } catch (e: IOException) {
                    Log.e(TAG, "再生の準備に失敗しました", e)
                }
            }

        }

        /**
         * SurfaceViewのサイズ等に変化があった場合、その後に実行される
         */
        override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {}

        /**
         * SurfaceViewが破棄される直前に実行される
         */
        override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
            mediaPlayer.release()
        }
    }
}
