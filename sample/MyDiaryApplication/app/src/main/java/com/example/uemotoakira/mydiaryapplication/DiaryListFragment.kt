package com.example.uemotoakira.mydiaryapplication

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import io.realm.Realm


/**
 * Fragmentのライフサイクル
 *
 * フラグメントの追加
 *  onAttach
 *  onCreate
 *  onCreateView
 *  onActivityCreated
 *  onStart
 *  onResume
 * フラグメントの実行中
 *  onPause
 *  onDestroyView
 *  onDestroy
 *  onDetach
 * フラグメントの終了
 *
 */

class DiaryListFragment : Fragment() {
    private lateinit var mListener: OnFragmentInteractionListener
    private lateinit var mRealm: Realm

    /**
     *
     */
    companion object {
        fun newInstance(): DiaryListFragment = DiaryListFragment()
    }

    /**
     * アクティビティに関連付けられたときに一度だけ呼ばれる
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString()
                    + " must implement OnFragmentInteractionListener")
        }
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRealm = Realm.getDefaultInstance()
    }

    /**
     *
     */
    override fun onCreateView(inflater: LayoutInflater
                              , container: ViewGroup?
                              , savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_diary_list, container, false)

        v.run {
            findViewById<RecyclerView>(R.id.recycler).apply {
                layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.VERTICAL }
                adapter = mRealm.where(Diary::class.java).findAll()
                                .let { DiaryRealmAdapter(activity, it, true) }
            }
        }

        return v
    }

    /**
     * onCreateView後に実行され -> onCreateOptionsMenuメソッドを実行する
     * setHasOptionsMenu:::フラグメントがメニューを持っているか設定
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     *
     */
    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    /**
     *
     */
    override fun onDetach() {
        super.onDetach()
    }

    /**
     * フラグメントからの通知を行うコールバック
     */
    interface OnFragmentInteractionListener { fun onAddDiarySelected() }

    /**
     * オプションメニューを初期化する
     * inflate:::メニューXMLファイルからメニューを構築する
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_diary_list, menu)
        MyUtils.tintMenuIcon(context, menu.findItem(R.id.menu_item_add_diary), android.R.color.white)
        MyUtils.tintMenuIcon(context, menu.findItem(R.id.menu_item_delete_all), android.R.color.white)
    }

    /**
     * オプションメニューがタップされたときに呼び出す
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_add_diary -> { if (mListener != null) mListener.onAddDiarySelected()
                                            return true
                                        }
            R.id.menu_item_delete_all -> {  mRealm.where(Diary::class.java).findAll()
                                            .run { mRealm.executeTransaction { deleteAllFromRealm()}}
                                            return true
                                        }
        }
        return false
    }
}
