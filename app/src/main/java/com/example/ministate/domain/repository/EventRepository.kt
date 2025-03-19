package com.example.ministate.domain.repository

import com.example.ministate.data.local.realm.Event
import com.example.ministate.data.local.realm.EventCategory
import com.example.ministate.domain.event.EventCategoryList
import com.example.ministate.domain.event.EventDetailsList
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun loadEventCatagories()
    suspend fun loadEventDetailsList()
    suspend fun storeEventDetails(eventDetailsList: EventDetailsList)
    suspend fun storeEventCatagories(eventCatagoryList: EventCategoryList)
    fun getEventCategoriesFlowProducer(): Flow<ResultsChange<EventCategory>>
    fun getEventCategoryById(id: String): String
    fun getEventById(eventId: String?): Event?
    fun deleteAll()
    fun getEventDetailsListFlowProducer(): Flow<ResultsChange<Event>>
}