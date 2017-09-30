package com.example.uemotoakira.application05

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

/**
 * android.osHandlerとMessageを使う方法
 */
class Main7Activity : AppCompatActivity() {

    lateinit var textViewMain7: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main7)

        textViewMain7  = findViewById<TextView>(R.id.textView7)

        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.button7_1).setOnClickListener({ button1Clicked() })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.button7_2).setOnClickListener({
            Toast.makeText(this@Main7Activity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })
    }

    private fun button1Clicked() {

        val THREAD_START = 0
        val THREAD_END = 1
        var handler: Handler = object : Handler() { override fun handleMessage(msg: Message) {
            when (msg.what) {
                THREAD_START -> textViewMain7.text = "実行開始"
                THREAD_END -> textViewMain7.text = "実行終了"
            }
        }
        }

        Thread(Runnable {
            handler.obtainMessage(THREAD_START).let { handler.sendMessage(it) }
            try { Thread.sleep(5000) } catch (e: InterruptedException) { e.printStackTrace() }
            handler.obtainMessage(THREAD_END).let { handler.sendMessage(it) }
        }).start()
    }
}
