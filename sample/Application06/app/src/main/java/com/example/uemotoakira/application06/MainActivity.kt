package com.example.uemotoakira.application06

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.EditText

import android.net.ConnectivityManager
import android.content.Context

import android.os.AsyncTask
import java.io.*
import java.net.Socket
import java.net.HttpURLConnection
import java.net.URL


/**
 * java.net.Socket:::HTTPだけではなく他の様々なプロトコルで通信を行う
 * java.net.HttpURLConnection:::HTTPプロトコルを使った通信を行うために特化したクラス
 */
class MainActivity : AppCompatActivity() {


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textViewResult)

        /**
         * Socket利用
         * ConnectivityManager
         *  getSystemService:::引数からシステムが提供するサービス用のオブジェクトを返す
         *  activeNetworkInfo:::現在接続中のデフォルトのネットワークについての詳細な情報をもつNetworkIfoオブジェクトを返す
         *  isConnected:::ネットワークが接続されている状態の場合はtrue、それ以外はfalse
         */
        findViewById<Button>(R.id.button).setOnClickListener {
            val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connMgr.activeNetworkInfo.run {
                if (isConnected) {
                    val editText = findViewById<EditText>(R.id.editTextUri)
                    editText.text.toString().let { MyHttpGetTask(textView).execute(it) }
                } else {
                    textView.text = "ネットワークが使えません"
                }
            }

        }

        /**
         * HttpURLConnection利用
         */
        findViewById<Button>(R.id.button2).setOnClickListener {
            val connMgr = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connMgr.activeNetworkInfo.run {
                if (isConnected) {
                    val editText = findViewById<EditText>(R.id.editTextUri2)
                    editText.text.toString().let { MyHttpGetTask2(textView).execute(it) }
                } else {
                    textView.text = "ネットワークが使えません"
                }
            }
        }

    }

    /**
     * Socket利用
     */
    private data class MyHttpGetTask(val textView:TextView) : AsyncTask<String, Void, String>() {
        override fun onPreExecute() { textView.text = "" }

        override fun doInBackground(vararg strings: String): String {

            var bw: BufferedWriter? = null
            var br: BufferedReader? = null

            val host = strings[0]
            val HTTP_GET = "GET / HTTP/1.0 \nHost: $host\nConnection: close\n\n"
            val stringBuffer = StringBuffer()
            val socket: Socket = Socket(host, 80)

            try {
                bw = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
                bw!!.run {
                    write(HTTP_GET)
                    flush()
                }

                br = BufferedReader(InputStreamReader(socket.getInputStream()))
                var line: String? = br!!.readLine()
                while (line != null){
                    stringBuffer.append(line)
                    line = br!!.readLine()
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                return "接続に失敗しました"
            } finally {
                try {
                    if (bw != null) bw!!.close()
                    if (br != null) br!!.close()
                    socket.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return stringBuffer.toString()
        }

        override fun onPostExecute(s: String) { textView.text = s }
    }

    /**
     * HttpURLConnection利用
     * 　openConnection():::指定されたURLに対するURLConnectionを返す
     */
    private data class MyHttpGetTask2(val textView:TextView) : AsyncTask<String, Void, String>() {

        override fun onPreExecute() { textView.text = "" }

        override fun doInBackground(vararg strings: String): String {
            var br: BufferedReader? = null
            val url = strings[0]
            val urlConnection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection

            val stringBuffer = StringBuffer()
            try {
                br = BufferedReader(InputStreamReader(urlConnection.inputStream))

                var line: String? = br!!.readLine()
                while (line != null){
                    stringBuffer.append(line)
                    line = br!!.readLine()
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                return "接続に失敗しました"
            } finally {
                try {
                    if (br!! != null) br!!.close()
                    urlConnection.disconnect()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return stringBuffer.toString()
        }

        override fun onPostExecute(s: String) { textView.text = s }
    }

}
