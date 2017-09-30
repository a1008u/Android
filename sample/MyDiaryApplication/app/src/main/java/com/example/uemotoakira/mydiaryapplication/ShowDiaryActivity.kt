package com.example.uemotoakira.mydiaryapplication

import android.os.Bundle

import android.support.v7.app.AppCompatActivity

import android.content.res.ColorStateList

import android.support.v7.graphics.Palette
import android.support.design.widget.CollapsingToolbarLayout

import android.support.v4.widget.NestedScrollView
import android.widget.TextView
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import io.realm.Realm
import com.example.uemotoakira.mydiaryapplication.R.id.fab
import android.support.v4.view.ViewCompat.setBackgroundTintList
import com.example.uemotoakira.mydiaryapplication.R.id.body
import android.graphics.Bitmap


class ShowDiaryActivity : AppCompatActivity() {
    private lateinit var mBodyText: String
    private lateinit var mRealm: Realm

    /**
     *
     */
    companion object {
        val DIARY_ID = "DIARY_ID"
        private val ERR_CD: Long = -1
    }

    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_diary)

        setSupportActionBar(findViewById<View>(R.id.toolbar) as Toolbar)

        /* fab設定（記事を共有するインテントを作成し発行する）
         * putExtra:::暗黙的なインテントに追加情報を設定する
         */
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).apply { putExtra(Intent.EXTRA_TEXT, mBodyText)
                                                             type = "text/plain"
                                                            })
        }

        //アクションバーのタイトルが下の画像のように戻るボタンになっていて直前のアクティビティに戻れる
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mRealm = Realm.getDefaultInstance()
        val diary = mRealm.where(Diary::class.java)
                          .equalTo("id", intent.getLongExtra(DIARY_ID, ERR_CD))
                          .findFirst()
        val layout = findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
        val body = findViewById<View>(R.id.body) as TextView
        val bytes = diary.run { layout.title = title
                                mBodyText = bodyText!!
                                body.text = bodyText
                                image }

        /* Paletteクラスを使って代表色を取得し、画面デザインへ反映する処理
            代表色を取得するメソッド（取得できない場合はメソッドの引数（デフォルトの色）を利用）
            鮮やかな色:::getVivrantColor
            鮮やかな色(暗):::getDarkVivrantColor
            鮮やかな色(明):::getLightVibrantColor
            落ち着いた色:::getMutedColor
            落ち着いた色(暗):::getDarkMutedColor
            落ち着いた色(明):::getLightMutedColor

        　　※generate:::Paletteクラスのインスタンスを生成します

           setExpandedTitleColor:::折りたたみ可能タイトル部分の文字色を指定する
           setContentScrimColor:::コンテンツのスクリム色（半透明で上に重ねられた状態）を指定します
           setBackgroundColor:::ビューの背景色を指定する
           backgroundTintList:::背景を着色する色を適用する
           valueOf:::単色からなるColorStateListを生成する*/
        if (bytes != null && bytes.isNotEmpty()) {

            val imageView = findViewById<View>(R.id.toolbar_image) as ImageView
            val palette = MyUtils.getImageFromByte(bytes).let { imageView.setImageBitmap(it)
                                                                Palette.from(it).generate()}

            val titleColor = palette.getLightVibrantColor(Color.WHITE)
            titleColor.let {
                layout.setExpandedTitleColor(it)
                body.setTextColor(it)
            }

            findViewById<NestedScrollView>(R.id.scroll_view).apply {
                setBackgroundColor(palette.getDarkMutedColor(Color.BLACK))
            }

            layout.setContentScrimColor(palette.getMutedColor(Color.DKGRAY))

            fab.backgroundTintList = palette.getLightMutedColor(Color.LTGRAY)
                                            .let { ColorStateList.valueOf(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

}

