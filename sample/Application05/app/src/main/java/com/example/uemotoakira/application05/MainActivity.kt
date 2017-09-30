package com.example.uemotoakira.application05

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    /**
     * 問題点
     *  処理1の実行中は、他の画面操作ができなくなる
     *
     * 解決策
     *  1.Viewクラスのpost()メソッドを使う方法
     *  2.ActivityクラスのrunOnUiTaskクラスを使う方法
     *  3.android.os.AsyncTaskクラスを使う方法
     *  4.android.osHandlerを使う方法
     *  　4-1.android.osHandlerとRunnableを使う方法
     *  　4-2.android.osHandlerとMessageを使う方法
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView  = findViewById<TextView>(R.id.textView)

        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.button1).setOnClickListener({ button1Clicked() })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.button2).setOnClickListener({
            Toast.makeText(this@MainActivity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })

        // Main2Activityのボタン
        findViewById<Button>(R.id.buttonMain2).setOnClickListener({
            val intent = Intent(this@MainActivity, Main2Activity::class.java)
            startActivity(intent)
        })

        // 1.Viewクラスのpost()メソッドを使う方法(Main3Activityのボタン)
        findViewById<Button>(R.id.buttonMain3).setOnClickListener({
            val intent = Intent(this@MainActivity, Main3Activity::class.java)
            startActivity(intent)
        })

        // 2.ActivityクラスのrunOnUiTaskクラスを使う方法(Main4Activityのボタン)
        findViewById<Button>(R.id.buttonMain4).setOnClickListener({
            val intent = Intent(this@MainActivity, Main4Activity::class.java)
            startActivity(intent)
        })

        // 3.android.os.AsyncTaskクラスを使う方法(Main5Activityのボタン)
        findViewById<Button>(R.id.buttonMain5).setOnClickListener({
            val intent = Intent(this@MainActivity, Main5Activity::class.java)
            startActivity(intent)
        })

        // 4-1.android.osHandlerとRunnableを使う方法(Main6Activityのボタン)
        findViewById<Button>(R.id.buttonMain6).setOnClickListener({
            val intent = Intent(this@MainActivity, Main6Activity::class.java)
            startActivity(intent)
        })

        // 4-2.android.osHandlerとMessageを使う方法(Main6Activityのボタン)
        findViewById<Button>(R.id.buttonMain7).setOnClickListener({
            val intent = Intent(this@MainActivity, Main7Activity::class.java)
            startActivity(intent)
        })

    }

    /**
     * ボタン１がクリックされた場合の処理内容を定義
     */
    private fun button1Clicked() {
        textView.run {
            text = "実行開始"
            try { Thread.sleep(5000) } catch (e: InterruptedException) { e.printStackTrace() }
            text = "実行終了"
        }
    }



}
