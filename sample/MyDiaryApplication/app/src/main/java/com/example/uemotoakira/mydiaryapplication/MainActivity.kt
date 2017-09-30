package com.example.uemotoakira.mydiaryapplication

import android.os.Bundle
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity
import io.realm.Realm

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity(), DiaryListFragment.OnFragmentInteractionListener  {

    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        mRealm = Realm.getDefaultInstance()

        // createTestData()
        showDiaryList()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    /**
     * testデータ
     */
    private fun createTestData() {
        mRealm.executeTransaction { realm ->
            // idフィールドの最大値を取得
            // createObjectではIDを渡してオブジェクトを生成する
            mRealm.where(Diary::class.java).max("id").run {
                var nextId: Long = if (this != null) this.toLong() + 1 else 0
                realm.createObject(Diary::class.java, nextId).apply {
                    title = "テストタイトル"
                    bodyText = "テスト本文です。"
                    date = "Feb 22"
                }
            }
        }
    }

    /**
     * DiaryListFragmentの内容を表示させる
     * findFragmentByTag:::トランザクションに追加するときにつけた名前でフラグメントを検索し、
     *                     見つかった場合はフラグメントを、見つからなかった場合はnull
     */
    private fun showDiaryList() {
        supportFragmentManager.apply {
            if(findFragmentByTag("DiaryListFragment") == null) {
                beginTransaction().apply {
                    add(R.id.content, DiaryListFragment() as Fragment, "DiaryListFragment")
                    commit()
                }
            }
        }
    }

    /**
     * 日記の新規作成画面を開く
     * replace:::アクティビティにフラグメントを追加する。
     * 　　　　　　すでに同じcontainerViewIdを持つフラグメントがあれば全て削除してから追加。
     * addToBackStack:::トランザクションをバックスタックに追加
     */
    override fun onAddDiarySelected() {
        var nextId: Long = mRealm.where(Diary::class.java).max("id")?.run {toLong() + 1} ?: 1
        mRealm.run{
            beginTransaction()
            createObject(Diary::class.java, nextId).apply { date = SimpleDateFormat("MMM d", Locale.US).format(Date()) }
            commitTransaction()
        }

        InputDiaryFragment.newInstance(nextId).let {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.content, it, "InputDiaryFragment")
                addToBackStack(null)
                commit()
            }
        }

    }

}
