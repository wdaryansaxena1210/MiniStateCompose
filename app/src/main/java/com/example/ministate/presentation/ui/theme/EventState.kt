package com.example.ministate.presentation.ui.theme

import com.example.ministate.data.local.realm.Event
import com.example.ministate.data.local.realm.EventCategory

data class EventState(
    val isLoading: Boolean = false,
    val eventCategories: List<EventCategory>? = null,
    val error: String? = null,
    val eventList: List<Event>? = null
)