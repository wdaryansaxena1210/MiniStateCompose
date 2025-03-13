package com.example.ministate.data.local.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Event : RealmObject {
    @PrimaryKey
    var id : String = ""
    var category: String = ""
    var cost: String = ""
    var duration: String = ""
    var location: String = ""
    var shortDesc: String = ""
    var subject : String = ""
    var phone : String = ""
}