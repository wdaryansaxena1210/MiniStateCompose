package com.example.ministate.presentation.xml.category_list_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ministate.R
import com.example.ministate.data.local.realm.EventCategory
import com.example.ministate.databinding.FragmentCategoryListBinding
import com.example.ministate.presentation.common.EventViewModel
import com.example.ministate.presentation.xml.category_list_screen.adapter.CategoryListAdapter
import kotlinx.coroutines.launch

class CategoryListFragment : Fragment() {
    private lateinit var binding: FragmentCategoryListBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var adapter: CategoryListAdapter
    private val categoryList = mutableListOf<EventCategory>()


    override fun onCreate(savedInstanceState: Bundle?) {
        //onCreate -> retrieve arguments/data
        //CreateView -> code to bind data
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EventViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeCategories() //observeCategoriesAndRenderANewListWhenCategoryChanges
        //1. update categoryList instance variable
        //2. categoryList is pass by reference so if it changes here, then the value of the variable
        //passed to the adapter also changed
        //3. now that adapter's instance variable has been changed, call the adapter.notifyChanges

    }

    private fun observeCategories() {
        lifecycleScope.launch {
             viewModel.state.collect { it ->
                it.eventCategories?.let { it1 -> categoryList.addAll(it1) }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CategoryListAdapter(categoryList, parentFragmentManager)
        binding.rvCategoryList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategoryList.adapter = adapter
    }


}