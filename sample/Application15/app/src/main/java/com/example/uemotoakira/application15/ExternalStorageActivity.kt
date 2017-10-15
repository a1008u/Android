package com.example.uemotoakira.application15

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.util.Log
import android.widget.Toast
import java.io.*





class ExternalStorageActivity : AppCompatActivity() {

    private val TAG = "My Data External File01"
    private val fileName = "TestExternalFile.txt"
    private val SEPARATOR = System.getProperty("line.separator")

    var externalFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_storage)

        val editText = findViewById<EditText>(R.id.editText3)
        val buttonSave = findViewById<Button>(R.id.buttonSave3)
        val buttonLoad = findViewById<Button>(R.id.buttonLoad3)

        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            externalFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        } else {
            // 外部ストレージが使用できない場合
            Toast.makeText(this@ExternalStorageActivity, "外部ストレージが使用できません", Toast.LENGTH_SHORT).show()
        }

        // データを保存する
        buttonSave.setOnClickListener {
            if (externalFile != null) {
                try {
                    // ファイル名からFileOutputStreamを作成し、PrintWriterを作成する
                    val fos = FileOutputStream(externalFile)
                    val pw = PrintWriter(BufferedWriter(
                            OutputStreamWriter(fos)))

                    // PrintWriterにEditTextの内容を出力する
                    val str = editText.text.toString()
                    pw.print(str)

                    // PrintWriterを閉じる
                    pw.close()
                } catch (e: Exception) {
                    Log.d(TAG, "ファイルの保存に失敗しました", e)
                }

            }
        }

        // データを読み込んで画面に表示する
        buttonLoad.setOnClickListener {
            if (externalFile != null) {
                // データを読み込んで画面に表示する
                try {
                    val fis = FileInputStream(externalFile)
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
                    Toast.makeText(this@ExternalStorageActivity, "ファイルが存在しません", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.d(TAG, "ファイルの読み込みに失敗しました", e)
                }

            }
        }

    }
}
