package com.example.uemotoakira.mydiaryapplication

import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.annotation.ColorRes
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.view.MenuItem
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * Created 6
 * 画像の処理
 */
object MyUtils {

    /**
     * データベースに格納してあるバイト配列を受け取って、Bitmapクラスのインスタンスに変換する
     * <flow>
     *     一旦格納されている画像のバイト配列を取得し、サイズを確認後(必要なら小さくする)、Bitmapを取得する
     * ・decodeByteArray:::指定されたバイト配列からBitmapクラスのインスタンスを作成する
     * ・inJustDecodeBounds:::trueでBitmapをメモリ上に展開せず、BitmapFactory.OptionsのインスタンスにBitmapの情報だけを取得する
     */
    fun getImageFromByte(bytes: ByteArray): Bitmap {
        val opt = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        opt.let { BitmapFactory.decodeByteArray(bytes, 0, bytes.size, it) }

        opt.apply {
            inJustDecodeBounds = false
            inSampleSize = if (outHeight * outWidth > 500000)
                              (Math.sqrt((outHeight * outWidth).toDouble() / 500000) + 1).toInt()
                           else 1
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, opt)
    }

    /**
     * BitmapをDBに格納できるようにバイト配列に変換する
     * compress:::Bitmapを指定した画像フォーマットに変換して指定したストリームへ渡す
     */
    fun getByteFromImage(bmp: Bitmap): ByteArray {
        return ByteArrayOutputStream().let {
            bmp.compress(Bitmap.CompressFormat.PNG, 100, it)
            it.toByteArray()
        }
    }

    /**
     * decodeStream:::ストリームからビットマップを生成
     */
    @Throws(IOException::class)
    fun getImageFromStream(resolver: ContentResolver, uri: Uri): Bitmap {
        var `in`: InputStream? = resolver.openInputStream(uri)
        val opt = BitmapFactory.Options()

        opt.let {
            it.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`in`, null, it)
            `in`!!.close()
        }

        opt.apply {
            inJustDecodeBounds = false
            if (outHeight * outWidth > 500000)
                inSampleSize = (Math.sqrt((outHeight * outWidth).toDouble() / 500000) + 1).toInt()
        }

        `in` = resolver.openInputStream(uri)
        val bmp = BitmapFactory.decodeStream(`in`, null, opt)
        `in`!!.close()

        return bmp
    }

    /**
     * アイコンを白く表示する
     */
    fun tintMenuIcon(context: Context, item: MenuItem, @ColorRes color: Int) {
        item.apply {
            icon.let { DrawableCompat.setTint(DrawableCompat.wrap(it)
                                              ,ContextCompat.getColor(context, color)) }
        }
    }
}
