package com.example.uemotoakira.application15

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent


class MainActivity : AppCompatActivity() {

    private val PREF_NAME = "NAME"
    private val PREF_ID = "ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextName =  findViewById<EditText>(R.id.editTextName)
        val editTextId = findViewById<EditText>(R.id.editTextId)
        val buttonLoad = findViewById<Button>(R.id.buttonLoad)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        val  sharedPref: SharedPreferences = getPreferences(MODE_PRIVATE)

        // 保存ボタンクリック時の処理
        buttonSave.setOnClickListener {
            // データを保存する
            val editor = sharedPref.edit()
            editor.putString(PREF_NAME, editTextName.text.toString())
            editor.putString(PREF_ID, editTextId.text.toString())
            editor.apply()  //　又は editor.commit();
        }

        // 読み込みボタンクリック時の処理
        buttonLoad.setOnClickListener {
            // データを読み込んで画面に表示する
            val name = sharedPref.getString(PREF_NAME, "")
            val id = sharedPref.getString(PREF_ID, "")
            editTextName.setText(name)
            editTextId.setText(id)
        }

        // InternalStorageActivityの実行ボタン
        findViewById<Button>(R.id.InternalStorage).setOnClickListener{
            Intent(this@MainActivity, InternalStorageActivity::class.java).let { startActivity(it)}
        }

        // ExternalStorageActivityの実行ボタン
        findViewById<Button>(R.id.ExternalStorage).setOnClickListener{
            Intent(this@MainActivity, ExternalStorageActivity::class.java).let { startActivity(it)}
        }

    }
}
