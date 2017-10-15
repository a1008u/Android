package com.example.uemotoakira.application11

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.MotionEvent
import android.view.View


class MainActivity : AppCompatActivity() {

    private val myCircleViewMap = HashMap<Int, MyCircleView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.activity_main)
        constraintLayout.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {

                    // 画面がタッチされた場合の処理
                    val pointerIndex = motionEvent.actionIndex
                    val pointerId = motionEvent.getPointerId(pointerIndex)

                    // 画像作成
                    val myCircleView = MyCircleView(this@MainActivity, 0.0F, 0.0F)
                    myCircleViewMap.put(pointerId, myCircleView)
                    myCircleView.xLoc = motionEvent.getX(pointerIndex)
                    myCircleView.yLoc = motionEvent.getY(pointerIndex)

                    // 画像表示
                    constraintLayout.addView(myCircleView)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                    // タッチが離れた場合の処理
                    val pointerIndex = motionEvent.actionIndex
                    val pointerId = motionEvent.getPointerId(pointerIndex)
                    val myCircleView = myCircleViewMap.remove(pointerId)
                    // 画像削除
                    constraintLayout.removeView(myCircleView)
                }
                MotionEvent.ACTION_MOVE -> {
                    // タッチした状態で動かした場合の処理
                    for (i in 0 until motionEvent.pointerCount) {
                        val pointerId = motionEvent.getPointerId(i)
                        val myCircleView = myCircleViewMap[pointerId]
                        if (myCircleView != null) {
                            myCircleView!!.xLoc = motionEvent.getX(i)
                            myCircleView!!.yLoc = motionEvent.getY(i)
                            // 画像を描画
                            myCircleView!!.invalidate()
                        }
                    }
                }
            }
            true
        }

        // button2---------------------
        findViewById<View>(R.id.button).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main2Activity::class.java))
        }


    }

     open inner class MyCircleView(context: Context,var xLoc: Float = 0.toFloat(), var yLoc: Float = 0.toFloat()) : View(context) {
        private val RADIUS = 100f // 円の半径
        private val paint = Paint()

        init {
            paint.style = Paint.Style.FILL
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawCircle(xLoc, yLoc, RADIUS, paint)
            Log.d("MyOnTouchListener01", "xLoc=$xLoc, yLoc=$yLoc")
        }
    }


}
