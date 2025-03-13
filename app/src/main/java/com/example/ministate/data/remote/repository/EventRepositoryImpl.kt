package com.example.ministate.data.remote.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.ministate.common.Resource
import com.example.ministate.data.local.realm.Event
import com.example.ministate.data.local.realm.EventCatagory
import com.example.ministate.data.remote.dto.EventCatagoryDTO
import com.example.ministate.data.remote.dto.EventCatagoryListDTO
import com.example.ministate.data.remote.dto.EventDetailsDTO
import com.example.ministate.data.remote.dto.EventDetailsListDTO
import com.example.ministate.data.remote.dto.toEventCatagory
import com.example.ministate.data.remote.dto.toEventDetails
import com.example.ministate.domain.event.EventCatagoryList
import com.example.ministate.domain.event.EventDetailsList
import com.example.ministate.domain.repository.EventRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventRepositoryImpl(context: Context) : EventRepository {

    val queue = Volley.newRequestQueue(context)
    val configuration = RealmConfiguration.create(schema = setOf(Event::class, EventCatagory::class))
    val realm = Realm.open(configuration)

    override suspend fun loadEventCatagories() {

        val url = "https://www.event.iastate.edu/api/events/?key=8aa084537a2184f6179c&categories=-1"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response ->
                val categoryListDto = EventCatagoryListDTO()

                for (i in 0 until response.length()) {
                    val jsonObj = response.getJSONObject(i)
                    val category = EventCatagoryDTO(
                        id = jsonObj.getString("id"),
                        long_title = jsonObj.getString("long_title"),
                        short_title = jsonObj.getString("short_title")
                    )
                    categoryListDto.add(category)
                }

                println(categoryListDto)

                val eventCategoryList = EventCatagoryList().apply{
                    addAll(categoryListDto.map { it.toEventCatagory() })
                }
                //emit this resource?
                val resource = Resource.Success(eventCategoryList) // T = EventCatagoryListDTO
                println("Event Catagory List = $eventCategoryList")

                //commit to realm
                CoroutineScope(Dispatchers.IO).launch {
                    storeEventCatagories(eventCategoryList)
                }

            },
            {error ->
                val resource = Resource.Error<EventCatagoryList>(message = error.message.toString())
            }
        )

        queue.add(request)
    }

    override suspend fun loadEventDetailsList(){
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
                val eventDetailsList = EventDetailsList().apply {
                    addAll(eventDetailsDtoList.map { it.toEventDetails() })
                }

//                println("Event Details List (mapped) = $eventDetailsList")
                val resource : Resource<EventDetailsList>
                //emit this resource?

                //commit to realm
                if(eventDetailsList.isNotEmpty()){
                    resource = Resource.Success(eventDetailsList)
                    CoroutineScope(Dispatchers.IO).launch {
                        storeEventDetails(eventDetailsList)
                    }
                }

            },
            {err ->
                val resourse = Resource.Error<EventDetailsList>(message=err.message.toString())
            }
        )

        queue.add(request)
    }

    override suspend fun storeEventDetails(eventDetailsList: EventDetailsList) {
            realm.writeBlocking {
                eventDetailsList.forEach { it ->
                    copyToRealm(Event().apply {
                        id = it.id
                        category = it.category
                        subject = it.subject
                        shortDesc = it.shortDesc
                        location = it.location
                        phone = it.phone
                        cost = it.cost
                    })
                }
            }
            realm.query<Event>().find().forEach { println("Event asdasdasda ${it.id}") }
            realm.writeBlocking { deleteAll() }
    }

    override suspend fun storeEventCatagories(eventCatagoryList: EventCatagoryList) {
        realm.writeBlocking{
            eventCatagoryList.forEach {
                copyToRealm(EventCatagory().apply {
                    id = it.id
                    long_title = it.long_title
                    short_title = it.short_title
                })
            }
        }
        realm.query<EventCatagory>().find().forEach { println(" Event Category asdasdasdasd ${it.id}") }
        realm.writeBlocking { deleteAll() }
    }
}