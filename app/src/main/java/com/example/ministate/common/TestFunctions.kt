package com.example.ministate.common

import android.content.Context
import com.example.ministate.data.repository.EventRepositoryImpl

class TestFunctions(val context: Context) {

    suspend fun testEventRepositoryGetEventCatagories(){
        val eventRepository = EventRepositoryImpl(context)
        eventRepository.loadEventCategories()
    }

    suspend fun testEventRepositoryGetEventDetailsList(){
        val eventRepository = EventRepositoryImpl(context)
        eventRepository.loadEventDetailsList()

    }
}