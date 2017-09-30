package com.example.uemotoakira.application02

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.*
import android.widget.Toast
import android.widget.AdapterView




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ボタン-------------------------------------------------------
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val textView = findViewById<TextView>(R.id.textView)
            findViewById<EditText>(R.id.editText).let {
                val text = it.text.toString()
                button.text = text
                textView.text = text
            }
        }


        // radioボタン-------------------------------------------------------
        var radioListener = View.OnClickListener { view ->
            findViewById<ConstraintLayout>((R.id.constrained)).apply{
                when(view.id){
                    R.id.redButton -> setBackgroundColor(Color.RED)
                    R.id.greenButton -> setBackgroundColor(Color.GREEN)
                    R.id.blueButton -> setBackgroundColor(Color.BLUE)
                }
            }
        }

        radioListener.let {
            findViewById<RadioButton>(R.id.redButton).setOnClickListener(it)
            findViewById<RadioButton>(R.id.blueButton).setOnClickListener(it)
            findViewById<RadioButton>(R.id.greenButton).setOnClickListener(it)
        }

        // listViewを使わない-----------------
        val items = arrayListOf("項目1","項目2","項目3")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items)
        findViewById<ListView>(R.id.listView).run {
            this.adapter = adapter
            onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l -> Toast.makeText(applicationContext
                                            ,adapterView.getItemAtPosition(i).toString() + " Clicked!"
                                            , Toast.LENGTH_SHORT).show()
                                           }
        }

        // listViewを利用①---------------------
        val items2 = resources.getStringArray(R.array.items2)
        val adapter2 = ArrayAdapter<String>(this, R.layout.listview_item, items2)
        findViewById<ListView>(R.id.listView2).run {
            this.adapter = adapter2
            onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l -> Toast.makeText(applicationContext
                    ,adapterView.getItemAtPosition(i).toString() + " Clicked!"
                    , Toast.LENGTH_SHORT).show()
            }
        }

        // listViewを利用③---------------------
        val adapter3 = ArrayAdapter.createFromResource(this, R.array.items3, R.layout.listview_item)
        findViewById<ListView>(R.id.listView3).run {
            this.adapter = adapter3
            onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view, i, l -> Toast.makeText(applicationContext
                    ,adapterView.getItemAtPosition(i).toString() + " Clicked!"
                    , Toast.LENGTH_SHORT).show()
            }
        }

        // button2---------------------
        findViewById<View>(R.id.button2).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main2Activity::class.java))
        }

        // button3---------------------
        findViewById<View>(R.id.button3).setOnClickListener {
            startActivity(Intent(this@MainActivity, Main3Activity::class.java))
        }

    }
}
