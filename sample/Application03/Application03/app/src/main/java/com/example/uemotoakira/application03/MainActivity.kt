package com.example.uemotoakira.application03



import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.uemotoakira.application03.dynamic.Main2Activity
import com.example.uemotoakira.application03.dynamic2.Main3Activity

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)

        // button2---------------------
        findViewById<View>(R.id.dynamic1).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main2Activity::class.java))
        }

        // button3---------------------
        findViewById<View>(R.id.dynamic2).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main3Activity::class.java))
        }
    }
}
