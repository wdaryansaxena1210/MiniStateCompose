package com.example.ministate.domain.event

import kotlin.time.Duration

data class EventDetails(
    val id : String,
    val category: String,
    val cost: String,
    val duration: String,
    val location: String,
    val shortDesc: String,
    val subject : String,
    val phone : String,
)
