package com.example.ministate.presentation.xml.event_list_screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ministate.data.local.realm.Event
import com.example.ministate.databinding.EventListItemBinding

class EventListAdapter(private var events: List<Event>) :
    RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.tvLocation.text = event.location
            binding.tvSubject.text = event.subject
            binding.tvShortDesc.text = event.shortDesc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = EventListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun updateList(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
