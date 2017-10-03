package com.example.uemotoakira.application04

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


/**
 * Intentクラスは「呼び出したいActivityを決めるための情報」と、「そのActivityに渡したいデータ」を格納する入れ物
 */
class MainActivity : Activity() {

    val SUB_ACTIVITY_REQUEST = 1

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *  明示的Intent(値をやり取りしない場合)
         *  Intent(呼び出し元のクラスのContext, 実行したいクラス)
         *  ActivityクラスはContextクラスの間接的なサブクラスなので、Activityから呼び出したい場合「this」を渡す。
         */
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this@MainActivity, SubActivity1::class.java)
            startActivity(intent)
        }

        /**
         *　明示的Intent(値をやり取りする場合)
         * startActivityForResult(実行したいActivityの情報や渡したいデータを格納したIntent, Activityを識別)
         */
        findViewById<Button>(R.id.button2).setOnClickListener {
            Intent(this@MainActivity, SubActivity2::class.java).let {
                val textString =  findViewById<EditText>(R.id.editText2).run { text.toString() }
                it.putExtra("EDIT_TEXT_KEY", textString)
                startActivityForResult(it, SUB_ACTIVITY_REQUEST)
            }
        }

        /**
         *  暗黙的Intent(同一アプリケーション内での実行)
         */
        findViewById<Button>(R.id.button3).setOnClickListener {
            // SubActivity3用
            val MYACTION = "com.example.uemotoakira.application04.SubActivity3"
            Intent(MYACTION).let { startActivity(it) }
        }

        /**
         *　暗黙的Intent(自作アプリケーションの実行)
         */
        findViewById<Button>(R.id.button4).setOnClickListener {
            // SubActivity4用
            val MYACTION4 = "com.example.uemotoakira.application04_1.SubActionX"
            Intent(MYACTION4).let { startActivity(it)}
        }


        /**
         *　暗黙的Intent(既存のアプリケーションの実行)
         * Uri.parseより、実行されるアプリケーションが異なる
         * http: https:　→　Webプラウザ
         * tel →　電話をかけるアプリケーション
         * mailto →　mailアプリケーション
         */
        findViewById<Button>(R.id.button5).setOnClickListener {
            val editText = findViewById<EditText>(R.id.editText5).text.toString()
            val intent = Uri.parse(editText).let {Intent(Intent.ACTION_VIEW, it)}
            startActivity(intent)
        }

        /**
         *　パーミッション
         * AndroidManifest.xmlに「uses-permission」を記載する
         */
        findViewById<Button>(R.id.button6).setOnClickListener {
            // SubActivity6用
            val MYACTION6 = "com.example.uemotoakira.appplication04_2.SubActivityY"
            Intent(MYACTION6).let { startActivity(it)}
        }

    }

    /**
     *　SubActivity2のボタン処理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == SUB_ACTIVITY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                data.getStringExtra("SUB_KEY").let { Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show() }
            }
        }
    }
}
