package com.example.uemotoakira.mydiaryapplication

import android.app.Application
import io.realm.Realm
import io.realm.Realm.setDefaultConfiguration
import io.realm.RealmConfiguration



/**
 * Create No 1
 * データベースの設定
 */
class MyDiaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        RealmConfiguration.Builder().build().let { Realm.setDefaultConfiguration(it) }
    }
}