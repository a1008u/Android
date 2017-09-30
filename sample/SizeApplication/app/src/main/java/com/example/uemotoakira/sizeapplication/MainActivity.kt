package com.example.uemotoakira.sizeapplication

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    private lateinit var pref:SharedPreferences // save data with keyValue
    private lateinit var editNeck:EditText
    private lateinit var editSleeve:EditText
    private lateinit var editWaist:EditText
    private lateinit var editInseam:EditText

    private val NECK = "NECK"
    private val SLEEVE = "SLEEVE"
    private val WAIST = "WAIST"
    private val INSEAM = "INSEAM"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.let {
            editNeck = findViewById<EditText>(R.id.neck).apply { setText(it.getString(NECK, "")) }
            editSleeve = findViewById<EditText>(R.id.sleeve).apply { setText(it.getString(SLEEVE, "")) }
            editWaist = findViewById<EditText>(R.id.waist).apply {setText(it.getString(WAIST, ""))}
            editInseam = findViewById<EditText>(R.id.inseam).apply {setText(it.getString(INSEAM, ""))}
        }

        findViewById<View>(R.id.height_button).setOnClickListener {
            startActivity(Intent(this@MainActivity, HeightActivity::class.java))
         }
    }

    fun onSaveTapped(view :View){
        pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit().apply { putString(NECK, editNeck.text.toString())
                                         putString(SLEEVE, editSleeve.text.toString())
                                         putString(WAIST, editWaist.text.toString())
                                         putString(INSEAM, editInseam.text.toString())}
        editor.commit()
    }
}
