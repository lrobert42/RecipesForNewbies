package com.example.recipesfornewbies.groceryList

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.databinding.GroceryListItemBinding
import com.example.recipesfornewbies.recipes.ExtendedIngredient
import com.example.recipesfornewbies.recipes.Recipe


//const val ITEM_VIEW_TYPE_HEADER = 0
const val ITEM_VIEW_TYPE_ITEM = 1

class GroceryListAdapter(vm: GroceryListViewModel) :
    ListAdapter<GroceryListAdapter.DataItem, RecyclerView.ViewHolder>(ExtendedIngredientDiffCallback()) {

    val viewModel = vm

    fun parseAndSubmitList(recipe: Recipe, ingredientList: List<ExtendedIngredient?>) {
        val items = when (ingredientList) {
             //   null -> listOf(DataItem.Header(recipe))
//                else -> listOf(DataItem.Header(recipe)) + ingredientList.map { DataItem.IngredientItem(it)}
            else -> ingredientList.map { DataItem.IngredientItem(it!!) }
        }
        Log.i("Adapter", "List submitted")
        submitList(items)
    }

    /******************OVERRIDING RECYCLER VIEW FUNCTIONS*************************/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
//            is HeaderViewHolder ->{
//                val recipeItem = getItem(position) as DataItem.Header
//                holder.bind(recipeItem.recipe)
//            }
            is IngredientViewHolder -> {
                val ingredientItem = getItem(position) as DataItem.IngredientItem
                holder.bind(ingredientItem.ingredient)
                Log.i("Adapter", "Binding")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.i("Adapter", "View Created")
        return when (viewType) {
        //    ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> IngredientViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
         //   is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.IngredientItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /************************DEFINING VIEW HOLDERS*********************/
//    class HeaderViewHolder(private var binding: DetailedHeaderBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(recipe: Recipe) {
//            binding.recipe = recipe
//            binding.executePendingBindings()
//        }
//        companion object {
//            fun from(parent: ViewGroup): HeaderViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = DetailedHeaderBinding.inflate(layoutInflater, parent, false)
//                return HeaderViewHolder(binding)
//            }
//        }
//    }

    class IngredientViewHolder(private var binding: GroceryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: ExtendedIngredient) {
            binding.ingredient = ingredient
            Log.i("Adapter", "Binding done")
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): IngredientViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GroceryListItemBinding.inflate(layoutInflater, parent, false)
                return IngredientViewHolder(binding)
            }
        }
    }


/**************DEFINING DIFFCALLBACKS TO IMPROVE EFFICIENCY**********************/

class ExtendedIngredientDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


/*********************DEFINING DATAITEM CLASS, WHICH HOLDS DATA********************/

sealed class DataItem {
    data class IngredientItem(val ingredient: ExtendedIngredient): DataItem() {
        override val id = ingredient.id!!.toLong()
    }

//    data class Header(val recipe: Recipe): DataItem() {
//        override val id = recipe.id.toLong()
//    }
//
    abstract val id: Long
    }
}