package com.example.uemotoakira.myschedulerapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import io.realm.Realm
import android.content.Intent
import android.widget.AdapterView
import android.view.View


class MainActivity : AppCompatActivity() {

    private lateinit var mRealm : Realm

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Realmの取得
        mRealm = Realm.getDefaultInstance()

        // 画面の[+ボタン]の操作
        findViewById<View>(R.id.fab).setOnClickListener {
            startActivity(Intent(this@MainActivity, ScheduleEditActivity::class.java))
        }

        // ListViewの表示及び編集
        findViewById<ListView>(R.id.listView).apply {
            adapter = ScheduleAdapter(mRealm.where(Schedule::class.java).findAll())
            onItemClickListener = AdapterView.OnItemClickListener {
                parent, _, position, _ ->
                val schedule = parent.getItemAtPosition(position) as Schedule
                startActivity(Intent(this@MainActivity, ScheduleEditActivity::class.java)
                             .putExtra("schedule_id", schedule.id))
            }
        }

    }


    /**
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
