package com.example.ministate.presentation.xml.category_list_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ministate.data.local.realm.EventCategory
import com.example.ministate.databinding.CategoryListItemBinding

class CategoryListAdapter(private val categoryList : List<EventCategory>) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: EventCategory) {
             binding.tvCategoryName.setText(category.short_title)
            binding.cvCategoryCard.setOnClickListener{
                println("open fragment with id = ${category.id}")
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