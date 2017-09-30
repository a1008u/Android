package com.example.uemotoakira.helloandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.tapHere).setOnClickListener({
            textView = findViewById<TextView>(R.id.textView2).apply {
                text = if (text === "ボタンがたっぷされました") getText(R.string.hello)
                       else "ボタンがたっぷされました"
            }

        })
    }


}
