package com.example.uemotoakira.application10

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.graphics.*
import java.util.*


class Main2Activity : AppCompatActivity() {

    val SLEEP_MILLI_SEC: Int = 100
    val REPEAT_COUNT: Int = 500



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.drawable01)
        val myView = MyView(this, bitmap)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.activity_main2)
        constraintLayout.addView(myView)

        Thread(Runnable {
            for (i in 0 until REPEAT_COUNT) {
                try {
                    Thread.sleep(SLEEP_MILLI_SEC.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                myView.postInvalidate()
                myView.move()
            }
        }).start()
    }

    private inner class MyView(context: Context, private val bitmap: Bitmap) : View(context) {
        private val STEP = 50
        internal var currentX: Float = 0.toFloat()
        internal var currentY: Float = 0.toFloat()
        internal var dx: Float = 0.toFloat()
        internal var dy: Float = 0.toFloat()
        internal var bitmapHeight: Int = 0
        internal var bitmapWidth: Int = 0
        internal var rotation: Float = 0.toFloat()
        internal var canvasWidth = 0
        internal var canvasHeight = 0

        private val mPainter = Paint()

        init {

            val display = windowManager.defaultDisplay
            val point = Point(0, 0)
            display.getSize(point) // Display Size
            val displayWidth = point.x
            val displayHeight = point.y
            bitmapHeight = bitmap.height
            bitmapWidth = bitmap.width


            val x0 = (displayWidth / 2).toFloat()
            val y0 = (displayHeight / 2).toFloat()

            val r = Random().nextFloat()

            dx = (2f * r - 1.0f) as Float * STEP
            dy = (2f * r - 1.0f) as Float * STEP

            currentX = x0 - bitmapWidth / 2
            currentY = y0 - bitmapHeight / 2

            mPainter.isAntiAlias = true
        }

        protected override fun onDraw(canvas: Canvas) {
            canvas.drawColor(Color.DKGRAY)
            val rotationDegree = 10f

            canvasWidth = canvas.width
            canvasHeight = canvas.height

            canvas.rotate(rotation, currentX + bitmapWidth / 2,
                    currentY + bitmapHeight / 2)
            rotation += rotationDegree

            canvas.drawBitmap(bitmap, currentX, currentY, mPainter)
        }


        fun move() {
            if (currentX + dx < 0) {
                dx = -dx
            }
            if (currentY + dy < 0) {
                dy = -dy
            }
            if (canvasWidth < currentX + dx + bitmapWidth.toFloat()) {
                dx = -dx
            }
            if (canvasHeight < currentY + dy + bitmapHeight.toFloat()) {
                dy = -dy
            }
            currentX += dx
            currentY += dy
        }
    }

}
