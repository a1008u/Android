package com.example.uemotoakira.application12

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaRecorder
import android.media.MediaPlayer
import android.media.AudioManager
import android.os.Environment
import android.util.Log
import android.view.View
import java.io.IOException


class Main2Activity : AppCompatActivity() {

    lateinit var buttonRecord: Button
    lateinit var buttonPlay: Button
    var isPlaying = false
    var isRecording = false
    val LABEL_RECORD = "Record"
    val LABEL_PLAY = "Play"
    val LABEL_STOP = "Stop"
    var fullFileName: String? = null
    var fileName = "record_data.3gp"

    var mediaRecorder: MediaRecorder? = null
    var mediaPlayer: MediaPlayer? = null
    private lateinit var audioManager: AudioManager

    var TAG = "MyMediaPlayerRecorder01"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // ディレクトリ＋ファイル名
        fullFileName = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName)

        // 録音ボタン
        buttonRecord = findViewById<Button>(R.id.buttonRecord)
        buttonRecord.setOnClickListener(View.OnClickListener { recordClicked() })

        // 再生ボタン
        buttonPlay = findViewById<Button>(R.id.buttonPlay)
        buttonPlay.setOnClickListener(View.OnClickListener { playClicked() })

        // AudioManagerを取得する + オーディオフォーカスを要求する
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
    }

    private fun recordClicked() {
        if (isRecording) {
            // 録音状態の場合は録音を停止する
            isRecording = false
            buttonRecord.text = LABEL_RECORD
            buttonPlay.isEnabled = true

            if (null != mediaRecorder) {
                mediaRecorder?.run { stop()
                                     reset()
                                     release()
                                    }
               mediaRecorder = null
            }
        } else {
            // 録音停止状態の場合は録音を開始する
            isRecording = true
            buttonRecord.text = LABEL_STOP
            buttonPlay.isEnabled = false

            mediaRecorder = MediaRecorder()
            mediaRecorder?.run { setAudioSource(MediaRecorder.AudioSource.MIC)
                                 setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                                 setOutputFile(fullFileName)
                                 setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                                 try {
                                    prepare()
                                    start()
                                 } catch (e: IOException) {
                                    Log.e(TAG, "録音できません", e)
                                 }
                               }
        }
    }

    private fun playClicked() {
        if (isPlaying) {
            // 再生状態の場合は再生を停止する
            isPlaying = false
            buttonPlay.text = LABEL_PLAY
            buttonRecord.isEnabled = true

            if (null != mediaPlayer) {
                mediaPlayer?.run { stop()
                                   reset()
                                   release()
                                 }
                mediaPlayer = null
            }
        } else {
            // 再生状態の場合は開始を停止する
            isPlaying = true
            buttonPlay.text = LABEL_STOP
            buttonRecord.isEnabled = false

            mediaPlayer = MediaPlayer()
            mediaPlayer?.run {
                try {
                    setDataSource(fullFileName)
                    prepare()
                    start()
                } catch (e: IOException) {
                    Log.e(TAG, "再生できません", e)
                }
            }
        }
    }

    // アプリケーション停止時はリソースを解放する
    override fun onPause() {
        super.onPause()
        if( null != mediaRecorder ) {
            mediaRecorder?.release()
            mediaRecorder = null
        }

        if( null != mediaPlayer ) {
            mediaPlayer?.release()
            mediaPlayer = null
        }

    }

    /**
     * AudioFocusの変化検知用リスナー
     */
    private var afChangeListener: AudioManager.OnAudioFocusChangeListener = object : AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(focusChange: Int) {

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                audioManager.abandonAudioFocus(this)

                if (null != mediaRecorder) {
                    mediaRecorder?.release()
                    mediaRecorder = null
                }

                if (null != mediaPlayer) {
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
            }
        }
    }
}
