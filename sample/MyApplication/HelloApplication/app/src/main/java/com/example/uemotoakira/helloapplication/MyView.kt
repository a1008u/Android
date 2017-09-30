package com.example.uemotoakira.helloapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

/**
 * Created by uemotoakira on 2017/08/28.
 */
class MyView(context: Context) : View(context) {

    var mX = 100F
    var mY = 100F
    var mVX = 1F
    var mVY = 1F

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.argb(255, 80, 116, 62))
        // canvas.drawColor(Color.GREEN)

        val paint = Paint()
        paint.color = Color.WHITE
        canvas.drawCircle(mX, mY, 100F, paint)

        if (mX > this.width)  mVX = -mVX
        else if (mX < 0) mVX = -mVX

        if (mY > this.height) mVY = -mVY
        else if ( mY < 0) mVY = -mVY

        mX += mVX
        mY += mVY

        // 一度viewを無効化したあとに、onDraw()メソッドが呼び出される。
        // onDraw() → invalidate() → onDraw()
        invalidate()

    }
}