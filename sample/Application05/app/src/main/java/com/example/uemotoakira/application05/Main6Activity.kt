package com.example.uemotoakira.application05

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

/**
 * android.osHandlerとRunnableを使う方法
 */
class Main6Activity : AppCompatActivity() {

    lateinit var textViewMain6: TextView
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main6)

        textViewMain6  = findViewById<TextView>(R.id.textView6)

        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.button6_1).setOnClickListener({ button1Clicked() })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.button6_2).setOnClickListener({
            Toast.makeText(this@Main6Activity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })
    }

    private fun button1Clicked() {

        Thread(Runnable {
            handler.post(Runnable { textViewMain6.text = "実行開始" })
            try { Thread.sleep(5000) } catch (e: InterruptedException) { e.printStackTrace() }
            handler.post(Runnable { textViewMain6.text = "実行終了" })
        }).start()
    }
}
