package com.example.ministate.presentation.xml.event_list_screen.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ministate.R
import com.example.ministate.data.local.realm.Event
import com.example.ministate.databinding.EventListItemBinding
import com.example.ministate.presentation.xml.event_detail_screen.EventDetailsFragment

class EventListAdapter(private var events: List<Event>, val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: EventListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.tvLocation.text = event.location
            binding.tvSubject.text = event.subject
            binding.tvShortDesc.text = event.shortDesc

            binding.root.setOnClickListener{
                val eventDetailsFragment = EventDetailsFragment().apply {

                    //HOW DO YOU PASS THE ENTIRE EVENT, put parceble or what?
                    //how do we do it in our actual code-base

                    arguments = Bundle().apply {
                        putString("subject", event.subject)
                        putString("location", event.location)
                        putString("cost", event.cost)
                        putString("duration", event.duration)
                        putString("phone", event.phone)
                        putString("shortDesc", event.shortDesc)
                    }
                }

                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, eventDetailsFragment)
                    ?.addToBackStack(null)
                    ?.commit()
                }
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
