package com.example.uemotoakira.stonepaperscissorsapplication

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var pref:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 起動時にデータをクリアする
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        editor = pref.edit().apply{ clear()
                                    commit() }
    }

    fun onJankenButtonTapped(view: View)
        = startActivity(Intent(this, ResultActivity::class.java)
                                .apply { putExtra("MY_HAND", view.id) })
}
