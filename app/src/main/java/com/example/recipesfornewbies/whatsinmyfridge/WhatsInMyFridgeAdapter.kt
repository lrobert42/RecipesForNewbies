package com.example.recipesfornewbies.whatsinmyfridge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.databinding.FridgeItemsBinding
import com.example.recipesfornewbies.ingredientsDatabase.Ingredients


class WhatsInMyFridgeAdapter(vm: WhatsInMyFridgeViewModel) : ListAdapter<Ingredients, WhatsInMyFridgeAdapter.IngredientViewHolder>(DiffCallback) {

    val viewModel = vm

    class IngredientViewHolder(private var binding: FridgeItemsBinding, var viewModel: WhatsInMyFridgeViewModel):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredients) {
            binding.viewModel = viewModel
            binding.ingredient = ingredient
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, viewModel: WhatsInMyFridgeViewModel): IngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FridgeItemsBinding.inflate(layoutInflater, parent, false)
                return IngredientViewHolder(binding, viewModel)
            }
        }
    }

    //Allows RecyclerView to see items that have changed
    companion object DiffCallback : DiffUtil.ItemCallback<Ingredients>() {
        override fun areItemsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ingredients, newItem: Ingredients): Boolean {
            return oldItem.ingredient_name == newItem.ingredient_name
        }
    }

    //Creation of viewHolder
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int) : IngredientViewHolder {
        return IngredientViewHolder.from(parent, viewModel)
    }

//Attributes data to viewHolder

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.bind(ingredient)
    }
}

