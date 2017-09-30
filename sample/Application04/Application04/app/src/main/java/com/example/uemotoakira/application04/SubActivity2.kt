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

    private val KEY: String = "EDIT_TEXT_KEY"
    private val SUB_KEY = "SUB_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub2)

        val sub2EditText = findViewById<EditText>(R.id.sub2EditText)
        var str = intent.getStringExtra(KEY)
        sub2EditText.setText(str)

        findViewById<Button>(R.id.sub2button).setOnClickListener {
            val newIntent = intent.putExtra(SUB_KEY, sub2EditText.text.toString())

            setResult(RESULT_OK, newIntent)
            finish()
        }



    }
}
