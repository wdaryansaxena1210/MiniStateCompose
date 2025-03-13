package com.example.ministate.data.local.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class EventCatagory: RealmObject {
    @PrimaryKey
    var id: String = ""
    var long_title: String = ""
    var short_title: String = ""
}