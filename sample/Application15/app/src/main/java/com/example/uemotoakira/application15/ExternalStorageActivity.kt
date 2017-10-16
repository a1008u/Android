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

        /**
         * getExternalStorageState(): String
         * 外部ストレージの状態を取得する（ストレージが存在して利用可能な場合はMEDIA_MOUNTEDを返す）
         *
         * getExternalFilesDir(type: String): File
         * 外部ストレージのディレクトリを取得する
         * type::nullまたは外部ストレージ以下のディレクトリを指定するために値を設定する
         * 　　　 nullを指定した場合は外部ストレージのルートディレクトリが指定される
         *
         */
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState())
            externalFile = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
        else
            // 外部ストレージが使用できない場合
            Toast.makeText(this@ExternalStorageActivity, "外部ストレージが使用できません", Toast.LENGTH_SHORT).show()

        // データを保存する
        findViewById<Button>(R.id.buttonSave3).setOnClickListener {
            if (externalFile != null) {
                try {
                    // ファイル名からFileOutputStreamを作成し、PrintWriterを作成する
                    val pw =  FileOutputStream(externalFile).let { PrintWriter(BufferedWriter(OutputStreamWriter(it)))}

                    // PrintWriterにEditTextの内容を出力 + 閉じる
                    pw.print(editText.text.toString())
                    pw.close()

                } catch (e: Exception) {
                    Log.d(TAG, "ファイルの保存に失敗しました", e)
                }

            }
        }

        // データを読み込んで画面に表示する
        findViewById<Button>(R.id.buttonLoad3).setOnClickListener {
            if (externalFile != null) {
                // データを読み込んで画面に表示する
                try {
                    // ファイルを一行ずつ読み込み、改行を付けながらStringBufferに格納する
                    val stringBuffer = StringBuffer()
                    val br = FileInputStream(externalFile).let { BufferedReader(InputStreamReader(it))}
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
                    Toast.makeText(this@ExternalStorageActivity, "ファイルが存在しません", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.d(TAG, "ファイルの読み込みに失敗しました", e)
                }

            }
        }

    }
}
