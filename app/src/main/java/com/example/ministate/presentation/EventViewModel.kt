package com.example.ministate.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ministate.data.local.realm.Event
import com.example.ministate.data.local.realm.EventCategory
import com.example.ministate.data.remote.repository.EventRepositoryImpl
import com.example.ministate.presentation.ui.theme.EventState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(application: Application) : AndroidViewModel(application) {


    //Dependencies
    private val eventRepository = EventRepositoryImpl(application)

    private val _state = MutableStateFlow(EventState())
    val state = _state.asStateFlow()

    init {
        println("init called")
        viewModelScope.launch {

            println("in viewmodel scope")
            var eventCatList : List<EventCategory>? = null
            var eventList : List<Event>? = null

            withContext(Dispatchers.IO){
            eventRepository.loadEventCatagories()
            eventRepository.loadEventDetailsList()
            }

            println("before getEventDetailsListCollector")

// Launch both collections in separate coroutines
            launch {
                eventRepository.getEventDetailsListFlowProducer().collect {
                    eventList = it.list.toList()
                    _state.update { it.copy(eventList = eventList) }
                    println("state updated eventstate = ${_state.value.eventList}")
                }
            }

            launch {
                eventRepository.getEventCatagoriesFlowProducer().collect {
                    eventCatList = it.list.toList()
                    _state.update { it.copy(eventCategories = eventCatList) }
                    println("state updated catagoreis = ${_state.value.eventCategories}")
                }
            }
        }

        println("get element 55549 = " + getEventById("55549"))
    }

    fun getEventById(eventId: String): Event? {

        return eventRepository.getEventById(eventId)
    }
}

