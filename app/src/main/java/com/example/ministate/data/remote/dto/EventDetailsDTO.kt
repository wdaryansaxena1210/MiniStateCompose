package com.example.ministate.data.remote.dto

data class EventDetailsDTO(
    val category: String,
    val channel_id: String,
    val cost: String,
    val date_posted: String,
    val duration: String,
    val email: String,
    val event_date: String,
    val id: String,
    val location: String,
    val phone: String,
    val short_desc: String,
    val subject: String,
    val views: String
)

fun EventDetailsDTO.toEventDetails() {

}