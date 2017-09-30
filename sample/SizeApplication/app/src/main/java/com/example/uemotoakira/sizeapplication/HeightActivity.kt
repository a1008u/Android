package com.example.uemotoakira.sizeapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.preference.PreferenceManager
import android.content.SharedPreferences
import android.widget.*

class HeightActivity:AppCompatActivity(){

    private lateinit var Height:TextView
    private lateinit var spinner:Spinner
    private lateinit var pref:SharedPreferences
    private lateinit var seekBar: SeekBar

    private val HEIGHT = "HEIGHT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)

        Height = findViewById(R.id.height)

        // spinner----------------------------------------------------------------
        spinner = findViewById<Spinner>(R.id .spinner).apply {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                /**
                 * 一覧から１つを選んだ時の処理
                 */
                override fun onItemSelected(parent: AdapterView<*>
                                            , view: View
                                            , position: Int
                                            , id: Long) {
                    // 選ばれた項目を取得してTextViewに表示
                    val item = parent.selectedItem as String
                    if (!item.isEmpty()) Height.text = item
                }

                /**
                 * 一覧で何も選択されなかった時の処理
                 */
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        // SeekBar----------------------------------------------------------------
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        seekBar = findViewById<SeekBar>(R.id.seekBar).apply {
            progress = pref.getInt(HEIGHT, 160)
            setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                // シークバーの値が変更された
                override fun onProgressChanged(seekBar: SeekBar
                                               , progress: Int
                                               , fromUser: Boolean) {
                    // 変更された値をintから文字列に変換してTextViewに表示
                    Height.text = progress.toString()
                }

                // シークバーのスライド開始
                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                // シークバーのスライド終了
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }

        // radio----------------------------------------------------------------
        findViewById<RadioGroup>(R.id.radioGroup).setOnCheckedChangeListener({
            _, checkedId -> run {findViewById<RadioButton>(checkedId).text.let { Height.text = it }}
        })
    }

    /**
     * Dispatch onPause() to fragments.
     */
    override fun onPause() {
        super.onPause()
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().run {
            putInt(HEIGHT, Integer.parseInt(Height.text.toString()))
            commit()
        }
    }
}