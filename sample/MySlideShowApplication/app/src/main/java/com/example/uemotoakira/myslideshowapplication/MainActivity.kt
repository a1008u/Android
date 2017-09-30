package com.example.uemotoakira.myslideshowapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.media.MediaPlayer
import android.os.Handler
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var mImageSwitcher: ImageSwitcher
    private var mImageResources = intArrayOf(
              R.drawable.slide00, R.drawable.slide01, R.drawable.slide02, R.drawable.slide03
            , R.drawable.slide04, R.drawable.slide05, R.drawable.slide06, R.drawable.slide07
            , R.drawable.slide08, R.drawable.slide09)
    private var mPosition = 0

    var mIsSlideshow = false

    /**
     * 定期的な処理の実行(TimerTaskを継承する)
     */
    inner class MainTimerTask : TimerTask() {
        override fun run() {
            if (mIsSlideshow) mHandler.post(Runnable { movePosition(1) })
        }
    }

    private var mTimer = Timer()
    private var mTimerTask: TimerTask = MainTimerTask()
    var mHandler = Handler()

    private lateinit var mMediaPlayer: MediaPlayer

    // --------------------------------------------------------------------------
    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ImageSwitcherの利用(画像切り替えに利用)
        mImageSwitcher = findViewById(R.id.imageSwitcher)
        mImageSwitcher.setFactory { ImageView(applicationContext) }
        mImageSwitcher.setImageResource(mImageResources[0])

        //　タイマーの処理
        mTimer.schedule(mTimerTask, 0, 5000)

        // MediaPlayerの処理
        mMediaPlayer = MediaPlayer.create(this, R.raw.sample)
        mMediaPlayer.isLooping = true
    }

    // Button---------------------------------------------------------------------
    /**
     * 画面の変更用メソッド
     */
    private fun movePosition(move: Int) {
        mPosition += move
        if (mPosition >= mImageResources.size) { mPosition = 0 }
        else if (mPosition < 0) { mPosition = mImageResources.size - 1 }
        mImageSwitcher.setImageResource(mImageResources[mPosition])
    }

    /**
     * 戻るボタン
     * アニメーション設定 + ドロイド君をフェードアウト
     */
    fun onPrevButtonTapped(view: View) {
        mImageSwitcher.setInAnimation(this, android.R.anim.slide_in_left)
        mImageSwitcher.setOutAnimation(this, android.R.anim.slide_out_right)
        movePosition(-1)

        var targetView = findViewById<View>(R.id.imageView)
        targetView.animate().setDuration(1000).alpha(0.0f)
    }

    /**
     * 次へボタン
     * アニメーション設定 + ドロイド君をフェードアウト
     */
    fun onNextButtonTapped(view: View) {
        mImageSwitcher.setInAnimation(this, android.R.anim.fade_in)
        mImageSwitcher.setOutAnimation(this, android.R.anim.fade_out)
        movePosition(1)

        val targetView = findViewById<View>(R.id.imageView)
        targetView.animate().setDuration(1000).alpha(0.0f)
    }

    /**
     * スライドショーボタン
     * スライドショーの状態をボタン押下で変更 + 状態に合わせてmp3の状態も変化
     */
    fun onSlideshowButtonTapped(view: View) {
        if(!mIsSlideshow){
            mMediaPlayer.start()
        } else {
            mMediaPlayer.pause()
            mMediaPlayer.seekTo(40000) // 再生する地点を指定する
        }
    }

    /**
     * ドロイド君の設定
     * duration : アニメーションする時間
     * rotation : ビューを回転させる(360度で5回)
     * setInterpolator : 跳ねるような効果を指定(ビューの移動後のy座標を指定)
     */
    fun onAnimationButtonTapped(view: View){
        view.animate().apply {
            duration = 1500
            rotation(360.0f * 5.0f)
            setInterpolator(BounceInterpolator()).y(view.y + 100)
        }
    }
}
