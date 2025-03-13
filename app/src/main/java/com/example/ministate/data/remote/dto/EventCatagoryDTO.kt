package com.example.ministate.data.remote.dto

import com.example.ministate.domain.event.EventCatagory

data class EventCatagoryDTO(
    val id: String,
    val long_title: String,
    val short_title: String
)

fun EventCatagoryDTO.toEventCatagory() : EventCatagory {
    return EventCatagory(
        id = this.id,
        long_title = this.long_title,
        short_title = this.short_title
    )
}