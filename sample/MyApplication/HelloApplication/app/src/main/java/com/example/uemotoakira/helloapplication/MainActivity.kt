package com.example.uemotoakira.helloapplication

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.content.Intent
import android.net.Uri


class MainActivity : AppCompatActivity() {

    private lateinit var btn :Button
    private lateinit var btn2 :Button
    private lateinit var btn3 :Button
    private lateinit var btn4 :Button

    private lateinit var mPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Tag", "onCreateが実行されました")


        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.button1)
        btn.text = "hello"

        mPlayer = MediaPlayer.create(this, R.raw.test)

        btn.setOnClickListener({
            val b = it as Button
            mPlayer.start()
            if(b.text === "こんちは") {
                b.text = "hello"
            } else {
                b.text = "こんちは"
            }
        })

        btn2 = findViewById(R.id.button2)
        btn2.setOnClickListener({
            var uri = Uri.parse("geo:0,0?q=Shibuya")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        })

        btn3 = findViewById(R.id.button3)
        btn3.setOnClickListener({
            val v = Intent(this@MainActivity, MyActivity::class.java)
            v.putExtra("vx", 10F)
            v.putExtra("vy", 10F)
            startActivity(v)
        })

        btn4 = findViewById(R.id.button4)
        btn4.setOnClickListener({
            var uri = Uri.parse("tel:080-1196-6363")
            startActivity(Intent(Intent.ACTION_DIAL, uri))
        })


    }
    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * 別のActivityが前面
     */
    override fun onPause() {
        super.onPause()
        mPlayer.stop()
        Log.d("Tag", "onPauseが実行されました")
    }

    /**
     * しばらくActivityが前面にこない
     */
    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }


}

/* ボタンイベントの書き方
button.setOnClickListener(object : View.OnClickListener {
    override fun onClick(v: View) {
        val b: Button = v as Button
        b.text = "こんにちは"
    }
})

button.setOnClickListener { v ->
    val b: Button = v as Button
    b.text = "こんにちは"
}
 */