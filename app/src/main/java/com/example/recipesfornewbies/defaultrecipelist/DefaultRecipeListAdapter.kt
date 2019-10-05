package com.example.recipesfornewbies.defaultrecipelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.databinding.RecipeCardViewBinding
import com.example.recipesfornewbies.recipes.Recipe



 /*******************************RANDOM RECIPES*******************************************/

class RandomRecipeListAdapter: ListAdapter<Recipe, RandomRecipeListAdapter.RandomRecipeViewHolder>(DiffCallback) {

    class RandomRecipeViewHolder(private var binding: RecipeCardViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RandomRecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeCardViewBinding.inflate(layoutInflater, parent, false)
                return RandomRecipeViewHolder(binding)
            }
        }
    }

    //Allows RecyclerView to see items that have changed
    companion object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }
    }


    //Creation of viewHolder
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RandomRecipeViewHolder {
        return RandomRecipeViewHolder.from(parent)
    }

//Attributes data to viewHolder

    override fun onBindViewHolder(holder: RandomRecipeViewHolder, position: Int) {
        val recipe = getItem(position)

        holder.bind(recipe)
    }

}
