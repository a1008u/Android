package com.example.uemotoakira.application04

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button

/**
 * 明示的Intent(値をやり取りする)
 */
class SubActivity2 : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2)

        val sub2EditText = findViewById<EditText>(R.id.sub2EditText)
        sub2EditText.setText(intent.getStringExtra("EDIT_TEXT_KEY"))

        // setResult(呼び出し元に処理結果の状態を返すための変数, 呼び出し元に返すためのデータを格納したIntent)
        findViewById<Button>(R.id.sub2button).setOnClickListener {
            intent.putExtra("SUB_KEY", sub2EditText.text.toString()).let { setResult(RESULT_OK, it) }
            finish()
        }
    }
}
