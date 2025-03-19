package com.example.ministate.data.repository

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.ministate.common.Resource
import com.example.ministate.data.local.realm.Event
import com.example.ministate.data.local.realm.EventCategory
import com.example.ministate.data.remote.dto.EventCatagoryDTO
import com.example.ministate.data.remote.dto.EventCatagoryListDTO
import com.example.ministate.data.remote.dto.EventDetailsDTO
import com.example.ministate.data.remote.dto.EventDetailsListDTO
import com.example.ministate.data.remote.dto.toEventCatagory
import com.example.ministate.data.remote.dto.toEventDetails
import com.example.ministate.domain.event.EventCategoryList
import com.example.ministate.domain.event.EventDetailsList
import com.example.ministate.domain.repository.EventRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.ResultsChange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EventRepositoryImpl(context: Context) : EventRepository {

    val queue = Volley.newRequestQueue(context)
    val configuration =
        RealmConfiguration.create(schema = setOf(Event::class, EventCategory::class))
    val realm = Realm.open(configuration)

    override suspend fun loadEventCatagories() {

        val url = "https://www.event.iastate.edu/api/events/?key=8aa084537a2184f6179c&categories=-1"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                realm.writeBlocking {deleteAll()}

                val categoryListDto = EventCatagoryListDTO()

                for (i in 0 until response.length()) {
                    val jsonObj = response.getJSONObject(i)
                    val category = EventCatagoryDTO(
                        id = jsonObj.getString("id"),
                        long_title = jsonObj.getString("long_title"),
                        short_title = jsonObj.getString("short_title")
                    )
                    categoryListDto.add(category)

                    val existingEventCatagory = realm.query<EventCategory>("id=$0", category.id).find()
                    if(existingEventCatagory.isNotEmpty()){continue}
                }


//                println(categoryListDto)

                val eventCategoryList = EventCategoryList().apply {
                    addAll(categoryListDto.map { it.toEventCatagory() })
                }
                //emit this resource?
                val resource = Resource.Success(eventCategoryList) // T = EventCatagoryListDTO
//                println("Event Catagory List = $eventCategoryList")


                //commit to realm
                CoroutineScope(Dispatchers.IO).launch {
                    storeEventCatagories(eventCategoryList)
                }

            },
            { error ->
                val resource = Resource.Error<EventCategoryList>(message = error.message.toString())
            }
        )

        queue.add(request)
    }

    override suspend fun loadEventDetailsList() {

        val url = "https://www.event.iastate.edu/api/events/?weeks=-1&key=8aa084537a2184f6179c"



        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { res ->

                val eventDetailsDtoList = EventDetailsListDTO()
                for (i in 0 until res.length()) {
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

                    val existingEvent = realm.query<Event>("id=$0", eventDetails.id).find()
                    if (existingEvent.isNotEmpty()) {continue}

                    eventDetailsDtoList.add(eventDetails)
                }
                val eventDetailsList = EventDetailsList().apply {
                    addAll(eventDetailsDtoList.map { it.toEventDetails() })
                }

//                println("Event Details List (mapped) = $eventDetailsList")
                val resource: Resource<EventDetailsList>
                //emit this resource?

                //commit to realm
                if (eventDetailsList.isNotEmpty()) {
                    resource = Resource.Success(eventDetailsList)
                }
                CoroutineScope(Dispatchers.IO).launch {
//                    println("Event Details List (mapped) STORING = $eventDetailsList")
                    storeEventDetails(eventDetailsList)
                }


            },
            { err ->
                val resourse = Resource.Error<EventDetailsList>(message = err.message.toString())
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
//        realm.query<Event>().find().forEach{println("Event asdasdasdasd ${it.id}")}
    }

    override suspend fun storeEventCatagories(eventCatagoryList: EventCategoryList) {
        realm.writeBlocking {
            eventCatagoryList.forEach {
                copyToRealm(EventCategory().apply {
                    id = it.id
                    long_title = it.long_title
                    short_title = it.short_title
                })
            }
        }
//        realm.query<EventCatagory>().find()
//            .forEach { println(" Event Category asdasdasdasd ${it.id}") }
    }

    override fun getEventCategoriesFlowProducer(): Flow<ResultsChange<EventCategory>> {
        return realm.query<EventCategory>().asFlow()
    }

    override fun getEventDetailsListFlowProducer(): Flow<ResultsChange<Event>> {
        return realm.query<Event>().asFlow()
    }

    override fun deleteAll(){
        realm.writeBlocking { deleteAll() }
    }

    override fun getEventById(eventId: String?): Event? {
        println("getting id $eventId")
        return realm.query<Event>("id=$0", eventId).find().firstOrNull()
    }

    override fun getEventCategoryById(id : String): String {
        return realm.query<EventCategory>("id=$0", id).find().firstOrNull()?.short_title ?:"title"
    }
}

