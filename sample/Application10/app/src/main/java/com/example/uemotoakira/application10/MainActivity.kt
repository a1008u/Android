package com.example.uemotoakira.application10

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout


/**
 * 【画像を表示する方法】
 * android.widget.ImageViewを使う:::単純な静止画のように、ほとんど更新しないグレフィックの描画に使う
 * android.graphics.Canvasを使う:::頻繁に書き換えを行うようなグラフィックの描画に使う
 */
class MainActivity : AppCompatActivity() {

    /**
     * 静的にImagevViewを使う(layoutのxmlを利用)
     * 動的にImageViewを使う
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 動的にImageViewを使う
        val imageView = ImageView(this).apply {

                            // drawableから取得　→　設定
                            val drawable = ContextCompat.getDrawable(this@MainActivity
                                                                        , R.drawable.drawable01)
                            setImageDrawable(drawable)

                            //　配置設定
                            var params = ConstraintLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT
                                                                        , RelativeLayout.LayoutParams.MATCH_PARENT)
                            layoutParams = params
                        }
        findViewById<ConstraintLayout>(R.id.activity_main).addView(imageView)

        //
        findViewById<Button>(R.id.canvas1).setOnClickListener{
            Intent(this@MainActivity, Main2Activity::class.java).let { startActivity(it) }
        }

        //
        findViewById<Button>(R.id.canvas2).setOnClickListener{
            Intent(this@MainActivity, Main2Activity::class.java).let { startActivity(it) }
        }

    }
}
