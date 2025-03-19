package com.example.ministate.presentation.xml.event_list_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ministate.R
import com.example.ministate.databinding.FragmentEventListBinding
import com.example.ministate.presentation.common.EventViewModel
import com.example.ministate.presentation.xml.event_list_screen.adapter.EventListAdapter
import kotlinx.coroutines.launch

class EventListFragment : Fragment() {
    private lateinit var binding: FragmentEventListBinding
    private lateinit var vm: EventViewModel
    private lateinit var adapter: EventListAdapter
    private  var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(requireActivity())[EventViewModel::class.java]
        category = arguments?.getString("category")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Observe ViewModel's state.eventList
        observeEventList()
    }

    private fun observeEventList() {
        lifecycleScope.launch {
            vm.state.collect { state ->
                state.eventList?.let { e ->
                    val events = e.filter { it.category== category }
                    adapter.updateList(events)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = EventListAdapter(emptyList(), fragmentManager = parentFragmentManager) // Initially empty list
        binding.rvEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvents.adapter = adapter
    }
}