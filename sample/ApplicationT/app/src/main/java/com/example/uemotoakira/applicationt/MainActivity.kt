package com.example.uemotoakira.applicationt

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var audioManager: AudioManager? = null
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var buttonPlay: Button? = null
    internal var afChangeListener: AudioManager.OnAudioFocusChangeListener = MyOnAudioFocusChangeListener()
    internal var canPlay: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPlay = findViewById<Button>(R.id.buttonPlay)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val musicVolume = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxMusicVolume = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

        val textVolume = findViewById<TextView>(R.id.textVolume) as TextView
        findViewById<TextView>(R.id.textVolume).run {
            text = musicVolume.toString()
            setOnClickListener { audioManager!!.playSoundEffect(AudioManager.FX_KEY_CLICK) }
        }

        findViewById<Button>(R.id.buttonDown).setOnClickListener {
            var volume = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            volume--
            if (volume < 0) volume = 0
            textVolume.text = volume.toString()
            audioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
                    AudioManager.FLAG_SHOW_UI)
        }

        findViewById<Button>(R.id.buttonUp).setOnClickListener {
            var volume = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            volume++
            if (maxMusicVolume < volume) volume = maxMusicVolume
            textVolume.text = volume.toString()
            audioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
                    AudioManager.FLAG_SHOW_UI)
        }

        buttonPlay!!.setOnClickListener {
            if (canPlay) {
                soundPool!!.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        buttonPlay!!.isEnabled = false

        val result = audioManager!!.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
        canPlay = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED

        if (soundPool == null) {
            soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
            soundId = soundPool!!.load(this, R.raw.sound01, 1)
            // SoundPoolにOnLoadCompleteListenerを設定する
            soundPool!!.setOnLoadCompleteListener { soundPool, sampleId, status ->
                // 準備ができたらボタンを押せるようにする
                if (0 == status) {
                    buttonPlay!!.isEnabled = true
                } else {
                    Log.i(TAG, "SoundPoolの設定に失敗しました")
                    finish()
                }
            }
        }

        audioManager!!.isSpeakerphoneOn = true
        audioManager!!.loadSoundEffects()
    }

    override fun onPause() {
        if (null != soundPool) {
            soundPool!!.unload(soundId)
            soundPool!!.release()
            soundPool = null
        }
        audioManager!!.isSpeakerphoneOn = false
        audioManager!!.unloadSoundEffects()
        audioManager!!.abandonAudioFocus(afChangeListener)

        super.onPause()
    }

    /**
     * AudioFocusの変化検知用のリスナー
     */
    internal inner class MyOnAudioFocusChangeListener : AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(focusChange: Int) {
            Log.d(TAG, "focusChange=" + focusChange)
            canPlay = focusChange == AudioManager.AUDIOFOCUS_GAIN
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                audioManager!!.abandonAudioFocus(afChangeListener)
            }
        }
    }

    companion object {

        private val TAG = "MyAudioManager01"
    }
}