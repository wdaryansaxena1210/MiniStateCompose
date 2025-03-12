package com.example.ministate.common

import android.content.Context
import com.example.ministate.data.remote.repository.EventRepositoryImpl

class TestFunctions(val context: Context) {

    suspend fun testEventRepositoryGetEventCatagories(){
        val eventRepository = EventRepositoryImpl(context)
        eventRepository.getEventCatagories()
    }

    suspend fun testEventRepositoryGetEventDetailsList(){
        val eventRepository = EventRepositoryImpl(context)
        eventRepository.getEventDetailsList("3")

    }
}