package com.example.uemotoakira.application12

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.media.AudioManager
import android.media.SoundPool
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.util.Log


/**
 * オーディオフォーカス
 *  Androidには複数のアプリケーションが同時に音を鳴らす場合に、どのように処理を行うかを決めるために、音を使うための「使用権」
 *  そのため、「フォーカスの要求」と「フォーカスの検知」を持たせることが必要
 *
 *  【フォーカスの要求】
 *      他のアプリケーションに大して、このアプリケーションがフォーカスを必要としているということを知らせる機能
 *
 *  【フォーカスの検知】
 *      他のアプリケーションが「フォーカス」を要求しているかを知るための機能で、その為にはリスナーを使う
 *
 * SoundPool
 *  音源データをプログラム起動などの適当なタイミングであらかじめ読み混んでおき、必要な場合にすぐ使える形に変換して格納しておくためのクラス
 *
 *
 */
class MainActivity : AppCompatActivity() {

    private var audioManager: AudioManager? = null
    private var soundPool: SoundPool? = null
    private var soundId: Int = 0
    private var buttonPlay: Button? = null
    internal var afChangeListener: AudioManager.OnAudioFocusChangeListener = MyOnAudioFocusChangeListener()
    internal var canPlay: Boolean = false

    /**
     * playSoundEffect(effectTye : int) :Unit 指定したタイプの効果音を鳴らす。
     * setStreamVolume(streamType : int, index int, flags int) :Unit 指定したストリームの音量を指定した値に指定する。
     *  streamType::ストリームの種類を指定
     *  index::音量を指定
     *  flags::実行時の動作に関するフラグを指定
     *
     * 【soundPool】
     * play(soundID : int, leftVolume : int, rightVolume : int, priority : int, loop :int, rate :float) : int
     * SoundPool内に格納されている指定された音を鳴らす。戻り値はsoundIDで失敗した場合0
     *  soundID::鳴らしたい音のidを指定
     *  leftVolume::左側の音量
     *  rightVolume::右側の音量
     *  priority::ストリームの優先順位を指定
     *  loop::繰り返し回数を指定
     *  rate::音を鳴らす速さを指定（0.5-2倍速の間）
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPlay = findViewById<Button>(R.id.buttonPlay)

        // AudioManagerを取得
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // テキストに現在の音楽用ボリューム表示
        val textVolume = findViewById<TextView>(R.id.textVolume) as TextView
        findViewById<TextView>(R.id.textVolume).run {
            text = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC).toString()
            setOnClickListener { audioManager!!.playSoundEffect(AudioManager.FX_KEY_CLICK) }
        }

        // TODO:: i should write a high Order function
        // 音量下げるボタン
        findViewById<Button>(R.id.buttonDown).setOnClickListener {
            var volume = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            volume--
            if (volume < 0) volume = 0
            textVolume.text = volume.toString()
            audioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)
        }

        // 音量上げるボタン
        findViewById<Button>(R.id.buttonUp).setOnClickListener {
            var volume = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC).let {
                volume++
                if (it < volume) volume = it
            }

            textVolume.text = volume.toString()
            audioManager!!.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)
        }

        // AudioFocusが取れている場合はSoundPoolの音を鳴らす
        buttonPlay!!.setOnClickListener {
            if (canPlay) soundPool!!.play(soundId
                                            , 1.0f
                                            , 1.0f
                                            , 1
                                            , 0
                                            , 1.0f)
        }

        // button2---------------------
        findViewById<View>(R.id.button2).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main2Activity::class.java))
        }

        // button3---------------------
        findViewById<View>(R.id.button3).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main3Activity::class.java))
        }

        // button4---------------------
        findViewById<View>(R.id.button4).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main4Activity::class.java))
        }

    }

    /**
     * オーディオフォーカスの取得やSoundPoolへの音源データの読み込みなどを行う
     * requestAudioFocus(i AudioManager.OnAudioFocusChangeListener, streamType: int, durationHint int) :int
     * オーディオフォーカスを要求
     *      成功::AUDIOFOCUS_REQUEST_GRANTED
     *      失敗::AUDIOFOCUS_REQUEST_FAILED
     *  i :: フォーカスの変更があった場合に実行されるリスナーを指定
     *  streamType :: ストリームの種類を指定
     *  durationHint :: フォーカスの要求をする場合、そのおよその長さを指定
     *
     * SoundPool(maxStreams: int, streamType: int, srcQuality: int)
     * →　現状はSoundPool.Builder()を利用する
     *  maxStreams :: SoundPoolのストリームの最大数を指定
     *  streamType :: ストリームの種類を指定
     *  srcQuality :: サンプルレートを変更する際の品質を指定
     *
     * load(context: Context, resId :int priority: int) : int
     * リソースIDを指定して音源データを読み込む(soundId)
     *  context::アプリケーションのContextを指定
     *  resId::リソースIDを指定
     *  priority::プリオリティを指定
     *
     */
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
    }

    /**
     * リソースの解放
     */
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
     * 他のアプリケーションからオーディオフォーカスの要求があった場合に呼び出されるリスナー
     */
    internal inner class MyOnAudioFocusChangeListener : AudioManager.OnAudioFocusChangeListener {
        override fun onAudioFocusChange(focusChange: Int) {
            Log.d(TAG, "focusChange=" + focusChange)
            canPlay = focusChange == AudioManager.AUDIOFOCUS_GAIN
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) audioManager!!.abandonAudioFocus(afChangeListener)
        }
    }

    companion object {
        private val TAG = "MyAudioManager01"
    }

}
