package com.example.uemotoakira.application10

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.graphics.*
import java.util.*


class Main2Activity : AppCompatActivity() {

    private val SLEEP_MILLI_SEC: Int = 100
    private val REPEAT_COUNT: Int = 500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // 画像のリソースファイルをBitmapデータに変換し、MyViewをインスタンス化して、レイアウトに設定
        val myView = BitmapFactory.decodeResource(resources, R.drawable.drawable01).let { MyView(this, it)}
        findViewById<ConstraintLayout>(R.id.activity_main2).run { addView(myView) }

        // 動画表示などの時間がかかる処理は、別スレッド内で行う
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
            val point = Point(0, 0).also { display.getSize(it) } // Display Size

            // 開始位置を設定
            val displayWidth = point.x
            val x0 = (displayWidth / 2).toFloat()
            val displayHeight = point.y
            val y0 = (displayHeight / 2).toFloat()

            // x,y方向のステップごとの移動距離(速度)を設定
            val r = Random().nextFloat()
            dx = (2f * r - 1.0f) * STEP
            dy = (2f * r - 1.0f) * STEP

            // 初期状態で図が画面中央になるように指定
            bitmapWidth = bitmap.width        // bitmapの長さ
            currentX = x0 - bitmapWidth / 2   // displayからbitmapまでの横の長さ

            bitmapHeight = bitmap.height      // bitmapの高さ
            currentY = y0 - bitmapHeight / 2  // displayからbitmapまでの横の高さ

            mPainter.isAntiAlias = true
        }

        /**
         * drawColor(塗りつぶしたい色)　Canvasを塗りつぶしたい色で指定
         * rotate(回転角度, 回転の中心のx座標, 回転の中心のy座標)　Canvasの座標を指定した座標を中心として時計回りに回転
         * drawBitmap(描画するBitmap, Bitmapの左側の位置, Bitmapの上側の位置, 描画に使うPaintを指定)
         */
        override fun onDraw(canvas: Canvas) {
            canvas.drawColor(Color.DKGRAY)

            canvasWidth = canvas.width
            canvasHeight = canvas.height

            canvas.rotate(rotation, currentX + bitmapWidth / 2,currentY + bitmapHeight / 2)
            val rotationDegree = 10f
            rotation += rotationDegree

            canvas.drawBitmap(bitmap, currentX, currentY, mPainter)
        }

        // Canvas周囲の範囲を超えるか調べ、超える場合は反対方向に移動させる
        fun move() {
            if (currentX + dx < 0) dx = -dx
            if (currentY + dy < 0) dy = -dy
            if (canvasWidth < currentX + dx + bitmapWidth.toFloat()) dx = -dx
            if (canvasHeight < currentY + dy + bitmapHeight.toFloat()) dy = -dy
            currentX += dx
            currentY += dy
        }
    }

}
