package com.example.uemotoakira.application05

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

/**
 * ActivityクラスのrunOnUiTaskクラスを使う方法
 */
class Main4Activity : AppCompatActivity() {

    lateinit var textViewMain4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        textViewMain4  = findViewById<TextView>(R.id.textView4)

        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.button4_1).setOnClickListener({ button1Clicked() })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.button4_2).setOnClickListener({
            Toast.makeText(this@Main4Activity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })
    }

    /**
     *
     */
    private fun button1Clicked() {
        Thread(Runnable {
            this@Main4Activity.runOnUiThread({ textViewMain4.text = "実行開始" })
            try { Thread.sleep(5000) } catch (e: InterruptedException) { e.printStackTrace() }
            this@Main4Activity.runOnUiThread({ textViewMain4.text = "実行終了" })
        }).start()
    }
}
