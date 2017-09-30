package com.example.uemotoakira.application05

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.uemotoakira.application05.R.id.textView
import android.os.AsyncTask



/**
 * android.os.AsyncTaskクラスを使う方法
 */
class Main5Activity : AppCompatActivity() {

    lateinit var textViewMain5: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        textViewMain5  = findViewById<TextView>(R.id.textView5)

        // ボタン１がクリックされた場合の処理
        findViewById<Button>(R.id.button5_1).setOnClickListener({
            MyAsyncTask(textViewMain5).execute(5)
        })

        // ボタン２がクリックされた場合の処理
        findViewById<Button>(R.id.button5_2).setOnClickListener({
            Toast.makeText(this@Main5Activity, "処理２実行中", Toast.LENGTH_SHORT).show()
        })
    }

    internal open class MyAsyncTask : AsyncTask<Int, Int, String> {

        lateinit var textViewMain5: TextView

        constructor(textViewMain5:TextView) : super() {
            this.textViewMain5 = textViewMain5
        }

        override fun onPreExecute() {
            textViewMain5.text = "実行開始"
        }

        override fun doInBackground(vararg integers: Int?): String {
            val count = integers[0]
            try {
                for (i in 0 until count!!) {
                    Thread.sleep(1000)
                    publishProgress(i)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return "実行終了"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            textViewMain5.text = values[0].toString()
        }

        override fun onPostExecute(s: String) {
            textViewMain5.text = s
        }
    }

}

