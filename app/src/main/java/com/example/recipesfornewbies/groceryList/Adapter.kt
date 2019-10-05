package com.example.recipesfornewbies.groceryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.databinding.GroceryListItemBinding
import com.example.recipesfornewbies.recipes.ExtendedIngredient


/*******************************RANDOM RECIPES*******************************************/

class Adapter(vm: GroceryListViewModel): ListAdapter<ExtendedIngredient, Adapter.GroceryItemViewHolder>(DiffCallback) {

    val viewModel = vm
    class GroceryItemViewHolder(private var binding: GroceryListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient, sameAisle: Boolean, viewModel: GroceryListViewModel) {

            binding.aisleTextView.isVisible = !sameAisle
            binding.viewModel = viewModel
            binding.ingredient = ingredient
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): GroceryItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroceryListItemBinding.inflate(layoutInflater, parent, false)
                return GroceryItemViewHolder(binding)
            }
        }
    }

    //Allows RecyclerView to see items that have changed
    companion object DiffCallback : DiffUtil.ItemCallback<ExtendedIngredient>() {
        override fun areItemsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ExtendedIngredient, newItem: ExtendedIngredient): Boolean {
            return oldItem.id == newItem.id
        }
    }


    //Creation of viewHolder
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GroceryItemViewHolder {
        return GroceryItemViewHolder.from(parent)
    }

//Attributes data to viewHolder

    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
        val ingredient = getItem(position)
        var sameAisle : Boolean = true
        sameAisle = position > 0 && ingredient.aisle == getItem(position - 1).aisle

        holder.bind(ingredient, sameAisle, viewModel)
    }

}
