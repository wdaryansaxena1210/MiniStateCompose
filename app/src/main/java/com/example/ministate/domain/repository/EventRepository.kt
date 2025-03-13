package com.example.ministate.domain.repository

import com.example.ministate.domain.event.EventCatagoryList
import com.example.ministate.domain.event.EventDetailsList

interface EventRepository {
    suspend fun loadEventCatagories()
    suspend fun loadEventDetailsList()
    suspend fun storeEventDetails(eventDetailsList: EventDetailsList)
    suspend fun storeEventCatagories(eventCatagoryList: EventCatagoryList)
}