package com.example.ministate.presentation.xml.event_detail_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ministate.R
import com.example.ministate.data.local.realm.Event
import com.example.ministate.databinding.FragmentEventDetailsBinding

class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private var subject: String? = null
    private var location: String? = null
    private var cost: String? = null
    private var duration: String? = null
    private var phone: String? = null
    private var shortDesc: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the event object from the arguments
        arguments?.let {
            subject = it.getString("subject")
            location = it.getString("location")
            cost = it.getString("cost")
            duration = it.getString("duration")
            phone = it.getString("phone")
            shortDesc = it.getString("shortDesc")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

            binding.tvEventSubject.text = subject
            binding.tvEventLocation.text = location
            binding.tvEventCost.text = cost
            binding.tvEventDuration.text = duration
            binding.tvEventPhone.text = phone
            binding.tvEventDescription.text = shortDesc

        return binding.root
    }
}