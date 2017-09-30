package com.example.uemotoakira.myschedulerapplication

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Realmを実行する
 */
class MySchedulerApplication:Application(){
    /**
     *
     */
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().build())
    }
}
