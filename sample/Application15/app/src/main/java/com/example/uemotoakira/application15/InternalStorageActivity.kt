package com.example.uemotoakira.application15

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.*


class InternalStorageActivity : AppCompatActivity() {

    private val TAG = "My Data Internal File01"
    private val fileName = "TestInternalFile.txt"
    private val SEPARATOR = System.getProperty("line.separator")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_storage)

        val editText = findViewById<EditText>(R.id.editText2)

        findViewById<Button>(R.id.buttonSave2).setOnClickListener {
            // データを保存する
            try {
                /**
                 * ファイル名からFileOutputStreamを作成し、PrintWriterを作成する
                 *
                 * openFileOutput(name String, mode int): FileOutputStream
                 * ファイル名とモードを指定してFileOutputStreamを開く
                 *  name::パス名なしのファイル名を指定
                 *  mode::MODE_PRIVATEまたはMODE_APPENDのどちらかを指定する
                 */
                val pw = openFileOutput(fileName, Context.MODE_PRIVATE).let {PrintWriter(BufferedWriter(OutputStreamWriter(it)))}

                // PrintWriterにEditTextの内容を出力 + 閉じる
                pw.print(editText.text.toString())
                pw.close()
            } catch (e: Exception) {
                Log.d(TAG, "ファイルの保存に失敗しました", e)
            }
        }

        // 読み込みボタンクリック時の処理
        findViewById<Button>(R.id.buttonLoad2).setOnClickListener {
            // データを読み込んで画面に表示する
            try {
                // 1.ファイル名からFileInputStreamを作成し、BufferedReaderを作成する
                // 2.ファイルを一行ずつ読み込み、改行を付けながらStringBufferに格納する
                /**
                 * openFileInput(name: String):FileInputStream
                 * ファイル名を指定してFileInputStreamを開く
                 *  name::パス名なしのファイル名を指定
                 */
                val stringBuffer = StringBuffer()
                val br = openFileInput(fileName).let { BufferedReader(InputStreamReader(it)) }
                val result: List<String> = br.useLines { lineSequences: Sequence<String> -> lineSequences.take(10).toList() }
                result.forEach{
                    stringBuffer.append(it)
                    stringBuffer.append(SEPARATOR)
                }

                // StringBufferの内容をEditTextに表示する
                editText.setText(stringBuffer.toString())

                // BufferedReaderを閉じる
                br.close()
            } catch (e: FileNotFoundException) {
                // ファイルが存在しない場合
                Toast.makeText (this@InternalStorageActivity, "ファイルが存在しません", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.d(TAG, "ファイルの読み込みに失敗しました", e)
            }
        }

    }
}
