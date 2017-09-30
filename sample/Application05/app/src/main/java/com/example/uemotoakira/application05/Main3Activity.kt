package com.example.uemotoakira.application05

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class Main3Activity : AppCompatActivity() {

    lateinit var textViewMain3: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        textViewMain3  = findViewById<TextView>(R.id.textView3)


        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.button3_1).setOnClickListener({ button1Clicked() })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.button3_2).setOnClickListener({
            Toast.makeText(this@Main3Activity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })
    }

    private fun button1Clicked() {
        Thread(Runnable {
            textViewMain3.post({ textViewMain3.text = "実行開始" })
            try { Thread.sleep(5000) } catch (e: InterruptedException) { e.printStackTrace() }
            textViewMain3.post({ textViewMain3.text = "実行終了" })
        }).start()
    }
}
