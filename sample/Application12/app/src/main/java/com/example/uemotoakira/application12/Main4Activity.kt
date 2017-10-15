package com.example.uemotoakira.application12

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class Main4Activity : AppCompatActivity() {

    private val VIDEO_PATH = "android.resource://" + "com.example.uemotoakira.application12/raw/video01"
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        // MediaControllerを作成し、一旦使用できないように設定する
        val mediaController = MediaController(this, true)
        mediaController.isEnabled = false

        // VideoViewを取得 + VideoViewにMediaControllerとファイルの場所を設定する
        videoView = findViewById<VideoView>(R.id.videoView)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(Uri.parse(VIDEO_PATH))

        // VideoViewに準備完了を待つリスナーを設定する
        // 準備が完了したらMediaControllerを使用可能にする
        videoView.setOnPreparedListener { mediaController.isEnabled = true }
    }

    override fun onPause() {
        if (videoView != null && videoView.isPlaying) {
            videoView.stopPlayback()
            // videoView = null
        }
        super.onPause()
    }
}
