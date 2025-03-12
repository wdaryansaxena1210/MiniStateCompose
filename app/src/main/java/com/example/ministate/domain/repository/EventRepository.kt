package com.example.ministate.domain.repository

import com.example.ministate.common.Resource
import com.example.ministate.data.remote.dto.EventDetailsDTO
import com.example.ministate.data.remote.dto.EventDetailsListDTO

interface EventRepository {
    suspend fun getEventCatagories()
    suspend fun getEventDetailsList(catagoryId : String)
}