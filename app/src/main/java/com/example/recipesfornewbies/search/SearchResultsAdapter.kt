package com.example.recipesfornewbies.defaultrecipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.databinding.SearchResultCardViewBinding
import com.example.recipesfornewbies.recipes.SearchResults


class SearchResultsAdapter : ListAdapter<SearchResults, SearchResultsAdapter.SearchResultsViewHolder>(DiffCallback) {

    class SearchResultsViewHolder(private var binding: SearchResultCardViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResults: SearchResults) {
            binding.searchResults = searchResults
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchResultsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchResultCardViewBinding.inflate(layoutInflater, parent, false)
                return SearchResultsViewHolder(binding)
            }
        }
    }

    //Allows RecyclerView to see items that have changed
    companion object DiffCallback : DiffUtil.ItemCallback<SearchResults>() {
        override fun areItemsTheSame(oldItem: SearchResults, newItem: SearchResults): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SearchResults, newItem: SearchResults): Boolean {
            return oldItem.id == newItem.id
        }
    }

    //Creation of viewHolder
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): SearchResultsViewHolder {
        return SearchResultsViewHolder.from(parent)
    }

//Attributes data to viewHolder

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }
}
