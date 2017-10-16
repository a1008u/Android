package com.example.uemotoakira.application15

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent

/**
 *
 * ・少量の単純な構造のデータ
 * ・内部ストレージ(Internal Storage)ファイル
 * ・外部ストレージ(External Storage)ファイル
 *
 *
 * 【少量の単純な構造のデータ】
 * ・android.content.SharedPreferences
 * ・android.content.SharedPreferences.Editor
 *
 *
 */
class MainActivity : AppCompatActivity() {

    private val PREF_NAME = "NAME"
    private val PREF_ID = "ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextName =  findViewById<EditText>(R.id.editTextName)
        val editTextId = findViewById<EditText>(R.id.editTextId)

        /**
         * getPreferences(mode: int) :SharedPreferences
         * SharedPreferencesオブジェクトを取得します
         *  mode::動作のモードを指定する引数
         */
        val sharedPref: SharedPreferences = getPreferences(MODE_PRIVATE)

        // 保存ボタンクリック時の処理
        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            /**
             * データを保存する
             * edit() :SharedPreferences.Editor
             * SharePreferences.Editorのオブジェクトを取得
             *
             * putString(key: String, value: String) :SharedPreference.Editor
             * データをキーと値の組として保存する
             */
            val editor = sharedPref.edit().apply { putString(PREF_NAME, editTextName.text.toString())
                                                   putString(PREF_ID, editTextId.text.toString())
                                                 }
            editor.apply()  //　又は editor.commit();　保存
        }

        // 読み込みボタンクリック時の処理
        findViewById<Button>(R.id.buttonLoad).setOnClickListener {
            /**
             * データを読み込んで画面に表示する
             *
             * getString(key:String, defValue)
             * キーに対応する値を取得する
             * 　　　　key::取り出したい値のキーを指定
             * 　defValue::キーが存在しない場合に返す値を設定
             */
            sharedPref.let {
                editTextName.setText(it.getString(PREF_NAME, ""))
                editTextId.setText(it.getString(PREF_ID, ""))
            }
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
