package com.example.uemotoakira.application05

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class Main2Activity : AppCompatActivity() {

    lateinit var textViewMain2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textViewMain2  = findViewById<TextView>(R.id.textView2)

        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.buttonMain2_1).setOnClickListener({ button1Clicked() })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.buttonMain2_2).setOnClickListener({
            Toast.makeText(this@Main2Activity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })
    }

    /**
     * Error:::Viewを作成したスレッド以外からそのViewにアクセスできない
     */
    private fun button1Clicked(){
        Thread(Runnable {
            textViewMain2.run {
                text = "実行開始"
                try { Thread.sleep(5000) } catch (e: InterruptedException) { e.printStackTrace() }
                text = "実行終了"
            }
        }).start()
    }
}
