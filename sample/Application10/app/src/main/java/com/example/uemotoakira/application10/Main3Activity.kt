package com.example.uemotoakira.application10

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.SurfaceHolder
import android.content.Context
import android.graphics.*
import android.view.SurfaceView
import java.util.*


class Main3Activity : AppCompatActivity() {

    val SLEEP_MILLI_SEC: Int = 100
    val REPEAT_COUNT: Int = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.drawable01)
        val mySurfaceView = MySurfaceView(this, bitmap)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.activity_main3)
        constraintLayout.addView(mySurfaceView)
    }

    private inner class MySurfaceView(context: Context, private val bitmap: Bitmap) : SurfaceView(context) {
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

        private val surfaceHolder: SurfaceHolder
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

            dx = (2.0f * r - 1.0f) * STEP
            dy = (2.0f * r - 1.0f) * STEP

            currentX = x0 - bitmapWidth / 2
            currentY = y0 - bitmapHeight / 2

            mPainter.isAntiAlias = true

            val mySurfaceHolderCallback = MySurfaceHolderCallback()
            surfaceHolder = holder
            surfaceHolder.addCallback(mySurfaceHolderCallback)
        }


        internal inner class MySurfaceHolderCallback : SurfaceHolder.Callback {
            private var mDrawingThread: Thread? = null

            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                mDrawingThread = Thread(Runnable {
                    for (i in 0 until REPEAT_COUNT) {
                        try {
                            Thread.sleep(SLEEP_MILLI_SEC.toLong())
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }

                        val canvas = surfaceHolder.lockCanvas()
                        if (null != canvas) {

                            drawCanvas(canvas)
                            surfaceHolder.unlockCanvasAndPost(canvas)
                            move()
                        }
                    }
                })
                mDrawingThread!!.start()
            }

            override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {}

            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {}


            private fun drawCanvas(canvas: Canvas) {
                canvas.drawColor(Color.DKGRAY)
                val rotationDegree = 10f

                canvasWidth = canvas.width
                canvasHeight = canvas.height

                canvas.rotate(rotation, currentX + bitmapWidth / 2,currentY + bitmapHeight / 2)
                rotation += rotationDegree

                canvas.drawBitmap(bitmap, currentX, currentY, mPainter)
            }


            private fun move() {
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
}
