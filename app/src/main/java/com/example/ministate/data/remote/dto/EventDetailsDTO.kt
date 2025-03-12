package com.example.ministate.data.remote.dto

import com.example.ministate.domain.event.EventDetails

data class EventDetailsDTO(
    val category: String,
    val channelId: String,
    val cost: String,
    val datePosted: String,
    val duration: String,
    val email: String,
    val eventDate: String,
    val id: String,
    val location: String,
    val phone: String,
    val shortDesc: String,
    val subject: String,
    val views: String
)

fun EventDetailsDTO.toEventDetails() : EventDetails{
    return EventDetails(
        id = id,
        category = category,
        cost = cost,
        duration = duration,
        location = location,
        shortDesc = shortDesc,
        subject = subject,
        phone = phone
    )
}