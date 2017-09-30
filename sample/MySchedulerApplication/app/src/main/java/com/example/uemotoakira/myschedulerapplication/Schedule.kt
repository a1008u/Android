package com.example.uemotoakira.myschedulerapplication

import io.realm.annotations.PrimaryKey
import io.realm.RealmObject
import java.util.*

open class Schedule : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var date: Date? = null
    var title: String? = null
    var detail: String? = null
}

