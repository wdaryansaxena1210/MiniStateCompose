package com.example.ministate.data.remote.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.ministate.common.Resource
import com.example.ministate.data.remote.dto.EventCatagoryDTO
import com.example.ministate.data.remote.dto.EventCatagoryListDTO
import com.example.ministate.data.remote.dto.EventDetailsDTO
import com.example.ministate.data.remote.dto.EventDetailsListDTO
import com.example.ministate.data.remote.dto.toEventDetails
import com.example.ministate.domain.event.EventDetailsList
import com.example.ministate.domain.repository.EventRepository

class EventRepositoryImpl(context: Context) : EventRepository {

    val queue = Volley.newRequestQueue(context)

    override suspend fun getEventCatagories() {

        val url = "https://www.event.iastate.edu/api/events/?key=8aa084537a2184f6179c&categories=-1"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response ->
                val categoryList = EventCatagoryListDTO()

                for (i in 0 until response.length()) {
                    val jsonObj = response.getJSONObject(i)
                    val category = EventCatagoryDTO(
                        id = jsonObj.getString("id"),
                        long_title = jsonObj.getString("long_title"),
                        short_title = jsonObj.getString("short_title")
                    )
                    categoryList.add(category)
                    println("Catagory list = $categoryList")
                }

                val resource = Resource.Success(categoryList) // T = EventCatagoryListDTO

            },
            {error ->
                val resource = Resource.Error<EventCatagoryListDTO>(message = error.message.toString())
            }
        )

        queue.add(request)
    }

    override suspend fun getEventDetailsList(catagoryId : String){
        val url = "https://www.event.iastate.edu/api/events/?weeks=-1&key=8aa084537a2184f6179c"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {res ->
                val eventDetailsDtoList = EventDetailsListDTO()
                for (i in 0 until res.length()){
                    val eventJsonObj = res.getJSONObject(i)
                    val eventDetails = EventDetailsDTO(
                        category = eventJsonObj.getString("category"),
                        channelId = eventJsonObj.getString("channel_id"),
                        cost = eventJsonObj.getString("cost"),
                        datePosted = eventJsonObj.getString("date_posted"),
                        duration = eventJsonObj.getString("duration"),
                        email = eventJsonObj.getString("email"),
                        eventDate = eventJsonObj.getString("event_date"),
                        id = eventJsonObj.getString("id"),
                        location = eventJsonObj.getString("location"),
                        phone = eventJsonObj.getString("phone"),
                        shortDesc = eventJsonObj.getString("short_desc"),
                        subject = eventJsonObj.getString("subject"),
                        views = eventJsonObj.getString("views"),
                    )
                    eventDetailsDtoList.add(eventDetails)
                }
                val eventDetailsList = eventDetailsDtoList
                    .filter{it.category == catagoryId}
                    .map{it.toEventDetails()}

                println("Event Details List (mapped) = $eventDetailsList")
                val resource = Resource.Success(eventDetailsList)
            },
            {err ->
                val resourse = Resource.Error<EventDetailsList>(message=err.message.toString())
            }
        )

        queue.add(request)
    }
}