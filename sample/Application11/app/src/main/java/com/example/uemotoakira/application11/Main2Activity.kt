package com.example.uemotoakira.application11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.GestureDetector
import android.widget.Button
import android.widget.EditText
import android.text.method.Touch.onTouchEvent







class Main2Activity : AppCompatActivity() {

    lateinit var gestureDetector:GestureDetector
    lateinit var editText :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        editText = findViewById<EditText>(R.id.editText)

        editText.keyListener = null

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener({
                editText.setText("")
        })


        gestureDetector = GestureDetector(this, MySimpleOnGestureListener())

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    /**
     * ログを表示
     */
    private fun showLog(msg: String) {
        Log.d("MyGestureDetector01", msg)
        var str = editText.text.toString()
        if ("" == str) {
            str = msg
        } else {
            str = str + "\n" + msg
        }
        editText.setText(str)
    }

    /**
     * SimpleOnGestureListenerのサブクラスを定義
     */
    internal inner class MySimpleOnGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
            showLog("SingleTapUp")
            return false
        }

        override fun onLongPress(e: MotionEvent) {
            showLog("LongPress")
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                              distanceX: Float, distanceY: Float): Boolean {
            showLog("Scroll")
            return false
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent,
                             velocityX: Float, velocityY: Float): Boolean {
            showLog("Fling")
            return false
        }

        override fun onShowPress(e: MotionEvent) {
            showLog("ShowPress")
        }

        override fun onDown(e: MotionEvent): Boolean {
            showLog("Down")
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            showLog("DoubleTap")
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            showLog("DoubleTapEvent")
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            showLog("SingleTapConfirmed")
            return false
        }

        override fun onContextClick(e: MotionEvent): Boolean {
            showLog("ContextClick")
            return false
        }
    }


}
