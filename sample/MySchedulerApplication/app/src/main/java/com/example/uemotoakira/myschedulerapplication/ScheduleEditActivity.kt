package com.example.uemotoakira.myschedulerapplication

import android.widget.Toast
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.widget.EditText
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import io.realm.Realm
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ScheduleEditActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm
    private lateinit var mDateEdit: EditText
    private lateinit var mTitleEdit: EditText
    private lateinit var mDetailEdit: EditText
    private lateinit var mDelete: Button

    /**
     * 削除ボタン表示判定
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_edit)

        mRealm = Realm.getDefaultInstance()
        mDateEdit = findViewById(R.id.dateEdit)
        mTitleEdit = findViewById(R.id.titleEdit)
        mDetailEdit = findViewById(R.id.detailEdit)
        mDelete = findViewById(R.id.delete)

        // -1は新規登録(削除ボタンは非表示)
        val scheduleId = intent.getLongExtra("schedule_id", -1)
        if (scheduleId.toInt() == -1) {
            mDelete.visibility = View.INVISIBLE
        } else {
            mRealm.where(Schedule::class.java)
                  .equalTo("id", scheduleId)
                  .findAll()
                  .first().let {
                                mDateEdit.setText(SimpleDateFormat("yyyy/MM/dd").format(it.date))
                                mTitleEdit.setText(it.title)
                                mDetailEdit.setText(it.detail)
                               }
            mDelete.visibility = View.VISIBLE
        }
    }

    /**
     *
     */
    fun onSaveTapped(view: View) {

        val date = try {
            SimpleDateFormat("yyyy/MM/dd").parse(mDateEdit.text.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
            Date()
        }

        val scheduleId = intent.getLongExtra("schedule_id", -1)
        if (scheduleId.toInt() != -1) {
            val results = mRealm.where(Schedule::class.java)
                                .equalTo("id", scheduleId).findAll()

            mRealm.executeTransaction {
                results.first().let { it.date = date
                                      it.title = mTitleEdit.text.toString()
                                      it.detail = mDetailEdit.text.toString()}
            }

            // 作成(makeメソッド) → 装飾(setActionTextColorメソッド) → 表示(showメソッド)
            Snackbar.make(findViewById<View>(android.R.id.content)
                            , "アップデートしました"
                            , Snackbar.LENGTH_LONG).setAction("戻る") { finish() }
                    .setActionTextColor(Color.YELLOW)
                    .show()
        } else {
            mRealm.executeTransaction { realm ->
                var nextId: Long = realm.where(Schedule::class.java)
                                        .max("id")?.run { this.toLong() } ?: 0
                realm.createObject(Schedule::class.java, nextId)
                     .let {
                            it.date = date
                            it.title = mTitleEdit.text.toString()
                            it.detail = mDetailEdit.text.toString()
                          }

             }
            
            Toast.makeText(this, "新規追加しました", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    /**
     *
     */
    fun onDeleteTapped(view: View) {
        val scheduleId = intent.getLongExtra("schedule_id", -1)
        if (scheduleId.toInt() != -1) {
            mRealm.executeTransaction { realm -> realm.where(Schedule::class.java)
                                                      .equalTo("id", scheduleId)
                                                      .findFirst()
                                                      .deleteFromRealm()
            }
            Toast.makeText(this, "削除しました", Toast.LENGTH_LONG).show()
        }
    }

    /**
     *
     */
    public override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }
}
