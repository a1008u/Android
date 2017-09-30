package com.example.uemotoakira.myschedulerapplication


import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection

import io.realm.RealmBaseAdapter;
import android.view.LayoutInflater
import java.text.SimpleDateFormat
import android.support.v7.widget.RecyclerView.ViewHolder




class ScheduleAdapter(data: OrderedRealmCollection<Schedule>) : RealmBaseAdapter<Schedule>(data) {

    private class ViewHolder {
        internal var date: TextView? = null
        internal var title: TextView? = null
    }

    /**
     * テーブルのセルを生成し、値をセットする。
     * getView:::リストビューのセルのデータが必要となると呼び出される
     *
     * ViewHolderクラス:::Viewオブジェクトを保持するためのもの
     * from:::LayoutInflaterクラスのインスタンスを生成する
     * inflate:::XMLからビューを生成する
     *
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        var viewHolder = if (convertView == null) {
                            ViewHolder().also {
                                convertView = LayoutInflater.from(parent.context)
                                                            .inflate(android.R.layout.simple_list_item_2
                                                                     , parent
                                                                     , false)
                                convertView!!.apply {
                                    it.date = findViewById<View>(android.R.id.text1) as TextView
                                    it.title = findViewById<View>(android.R.id.text2) as TextView
                                    tag = it
                                }

                            }
                         } else { convertView!!.tag as ViewHolder }

        viewHolder.apply {
            val schedule = adapterData!![position]
            date!!.text = SimpleDateFormat("yyyy/MM/dd").format(schedule.date)
            title!!.text = schedule.title
        }

        return convertView!!
    }

}
