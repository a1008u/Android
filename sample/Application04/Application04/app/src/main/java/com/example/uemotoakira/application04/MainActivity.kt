package com.example.uemotoakira.application04

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast



class MainActivity : Activity() {

    // SubActivity2用
    private val KEY: String = "EDIT_TEXT_KEY"
    private val SUB_KEY = "SUB_KEY"
    private val SUB_ACTIVITY_REQUEST = 1

    // SubActivity3用
    val MYACTION = "com.example.uemotoakira.application04.SubActivity3"

    // SubActivity4用
    val MYACTION4 = "com.example.uemotoakira.application04_1.SubActionX"

    // SubActivity6用
    val MYACTION6 = "com.example.uemotoakira.appplication04_2.SubActivityY"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         *
         */
        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(this@MainActivity, SubActivity1::class.java)
            startActivity(intent)
        }

        /**
         *
         */
        findViewById<Button>(R.id.button2).setOnClickListener {

            val mainEditText = findViewById<EditText>(R.id.editText2)
            val textString = mainEditText.text.toString()

            val intent = Intent(this@MainActivity, SubActivity2::class.java)
            intent.putExtra(KEY, textString)

            startActivityForResult(intent, SUB_ACTIVITY_REQUEST)
        }

        /**
         *
         */
        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(MYACTION)
            startActivity(intent)
        }

        /**
         *
         */
        findViewById<Button>(R.id.button4).setOnClickListener {
            Intent(MYACTION4).let { startActivity(it)}
        }


        /**
         *
         */
        findViewById<Button>(R.id.button5).setOnClickListener {

            val editText = findViewById<EditText>(R.id.editText5)
            val uri = Uri.parse(editText.text.toString())
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        /**
         *
         */
        findViewById<Button>(R.id.button6).setOnClickListener {
            Intent(MYACTION6).let { startActivity(it)}
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == SUB_ACTIVITY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val str = data.getStringExtra(SUB_KEY)

                Toast.makeText(this@MainActivity, str, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
