package com.example.uemotoakira.mydiaryapplication

import io.realm.RealmRecyclerViewAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import android.support.v4.app.FragmentActivity
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.content.Intent


/**
 * Created 5
 * card_layout用の設定
 */
class DiaryRealmAdapter(internal var context: FragmentActivity,
                        data: OrderedRealmCollection<Diary>?,
                        autoUpdate: Boolean) : RealmRecyclerViewAdapter<Diary, DiaryRealmAdapter.DiaryViewHolder>(data, autoUpdate) {

    /**
     * cardViewから情報を取得
     */
    class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView    = itemView.findViewById<View>(R.id.title) as TextView
        var bodyText: TextView = itemView.findViewById<View>(R.id.body) as TextView
        var date: TextView     = itemView.findViewById<View>(R.id.date) as TextView
        var photo: ImageView   = itemView.findViewById<View>(R.id.diary_photo) as ImageView
    }

    /**
     * card_layout１つ１つに処理を設定する
     * RecyclerViewが新しいRecyclerView.ViewHolderを必要とするときに呼び出される
     * また、詳細情報を表示させる
     * return DiaryViewHolderのインスタンス
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return LayoutInflater.from(parent.context)
                             .inflate(R.layout.card_layout, parent, false)
                             .let {
                                 DiaryViewHolder(it).apply {
                                     itemView.setOnClickListener {
                                         val diaryId = data!![adapterPosition].run { id }
                                         val intent = Intent(context, ShowDiaryActivity::class.java)
                                                      .putExtra(ShowDiaryActivity.DIARY_ID, diaryId)
                                         context.startActivity(intent)
                                     }
                                 }
                             }
    }

    /**
     * 指定された位置にデータ(card_layou)を表示するためにRecyclerViewを呼び出す
     * RecyclerViewが行の内容を表示する前に呼び出される。
     *　setImageBitmap:::BitmapをiImageViewに設定する。
     *  Realmに画像を格納する場合、Bitmapデータ　→　バイト列に変更してから格納する
     *  Realmの格納の画像を利用する場合、バイト列　→　Bitmapデータに変更してから格納する
     */
    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        var diary = data!![position] as Diary
        diary.let {
            holder.apply {
                title.text = it.title
                bodyText.text = it.bodyText
                date.text = it.date
                if(it.image != null && it.image!!.isNotEmpty())
                  it.apply { photo.setImageBitmap(MyUtils.getImageFromByte(it.image!!)) }
            }
        }
    }

}
