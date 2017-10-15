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
        val buttonSave = findViewById<Button>(R.id.buttonSave2)
        val buttonLoad = findViewById<Button>(R.id.buttonLoad2)

        buttonSave.setOnClickListener {
            // データを保存する
            try {
                // ファイル名からFileOutputStreamを作成し、PrintWriterを作成する
                val fos = openFileOutput(fileName, Context.MODE_PRIVATE)
                val pw = PrintWriter(BufferedWriter(OutputStreamWriter(fos)))

                // PrintWriterにEditTextの内容を出力する
                val str = editText.text.toString()
                pw.print(str)

                // PrintWriterを閉じる
                pw.close()
            } catch (e: Exception) {
                Log.d(TAG, "ファイルの保存に失敗しました", e)
            }
        }

        // 読み込みボタンクリック時の処理
        buttonLoad.setOnClickListener {
            // データを読み込んで画面に表示する
            try {

                // ファイル名からFileInputStreamを作成し、BufferedReaderを作成する
                val fis = openFileInput(fileName)
                val br = BufferedReader(InputStreamReader(fis))

                // ファイルを一行ずつ読み込み、改行を付けながらStringBufferに格納する
                val stringBuffer = StringBuffer()
                val result: List<String> = br.useLines { lineSequences: Sequence<String> ->
                                                lineSequences
                                                        .take(10)
                                                        .toList()
                                            }
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
                Toast.makeText (this@InternalStorageActivity,
                        "ファイルが存在しません", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.d(TAG, "ファイルの読み込みに失敗しました", e)
            }
        }

    }
}
