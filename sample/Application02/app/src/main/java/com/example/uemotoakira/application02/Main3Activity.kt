package com.example.uemotoakira.application02

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast


class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val inflater = layoutInflater as LayoutInflater
        val layout = findViewById<RelativeLayout>(R.id.layout_for_dynamicLayout)
        val button = findViewById<View>(R.id.button) as Button

       button.setOnClickListener { view ->
           // レイアウト上のビューを一旦削除
           layout.removeAllViews()
           // レイアウトを表示
           inflater.inflate(R.layout.dynamic, layout)
       }

    }

    /**
     * メニュー押下時に起動
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    /**
     * メニュー項目押下後に起動
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> Toast.makeText(this, "項目１", Toast.LENGTH_SHORT).show()
            R.id.item2 -> Toast.makeText(this, "項目２", Toast.LENGTH_SHORT).show()
            R.id.item3 -> Toast.makeText(this, "項目３", Toast.LENGTH_SHORT).show()
            R.id.item3_1 -> Toast.makeText(this, "項目３－１", Toast.LENGTH_SHORT).show()
            R.id.item3_2 -> Toast.makeText(this, "項目３－２", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
