package com.example.uemotoakira.mydiaryapplication

import io.realm.annotations.PrimaryKey
import io.realm.RealmObject



/**
 * Created 2
 */
open class Diary : RealmObject() {
    @PrimaryKey var id: Long = 0
                var title: String? = null
                var bodyText: String? = null
                var date: String? = null
                var image: ByteArray? = null
}
