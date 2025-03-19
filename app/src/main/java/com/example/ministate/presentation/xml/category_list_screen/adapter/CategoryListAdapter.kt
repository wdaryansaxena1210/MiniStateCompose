package com.example.ministate.presentation.xml.category_list_screen.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ministate.R
import com.example.ministate.data.local.realm.EventCategory
import com.example.ministate.databinding.CategoryListItemBinding
import com.example.ministate.presentation.xml.event_list_screen.EventListFragment

class CategoryListAdapter(private val categoryList : List<EventCategory>, val fragmentManager: FragmentManager)
    : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: EventCategory) {
             binding.tvCategoryName.setText(category.short_title)
            binding.cvCategoryCard.setOnClickListener{
                val eventListFragment = EventListFragment().apply { arguments= Bundle().apply { putString("category", category.id) } }

                println("event list fragment made. navigating to fragment")
                fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, eventListFragment)
                    .addToBackStack(null)
                    .commit()

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryListAdapter.ViewHolder {
        val binding = CategoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListAdapter.ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}