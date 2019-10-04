package com.example.recipesfornewbies.detailedrecipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesfornewbies.databinding.DetailedFooterBinding
import com.example.recipesfornewbies.databinding.DetailedHeaderBinding
import com.example.recipesfornewbies.databinding.StepViewBinding
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.recipes.Step


const val ITEM_VIEW_TYPE_HEADER = 0
const val ITEM_VIEW_TYPE_ITEM = 1
const val ITEM_VIEW_TYPE_FOOTER = 2

class DetailedRecipeAdapter(vm: DetailedRecipeViewModel) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(StepDiffCallback()) {

    val viewModel = vm

    fun parseAndSubmitList(recipe: Recipe, stepList: List<Step>?, footer: List<Recipe>?) {
            val items = when (stepList) {
                null -> when(footer){
                    null -> listOf(DataItem.Header(recipe))
                    else -> listOf(DataItem.Header(recipe)) + footer.map{DataItem.FooterItem(it)}
                }
                else -> when(footer){
                    null -> listOf(DataItem.Header(recipe)) + stepList.map { DataItem.StepItem(it)}
                    else -> listOf(DataItem.Header(recipe)) + stepList.map { DataItem.StepItem(it)} + footer.map{DataItem.FooterItem(it)}
                }
            }
                submitList(items)
        }

    /******************OVERRIDING RECYCLER VIEW FUNCTIONS*************************/

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder ->{
                val recipeItem = getItem(position) as DataItem.Header
                holder.bind(recipeItem.recipe)
            }
            is StepViewHolder -> {
                val stepItem = getItem(position) as DataItem.StepItem
                holder.bind(stepItem.step)
            }
            is FooterViewHolder ->{
                val footerItem = getItem(position) as DataItem.FooterItem
                holder.bind(footerItem.recipeFooter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> StepViewHolder.from(parent)
            ITEM_VIEW_TYPE_FOOTER -> FooterViewHolder.from(parent, viewModel)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.StepItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.FooterItem -> ITEM_VIEW_TYPE_FOOTER
        }
    }

    /************************DEFINING VIEW HOLDERS*********************/
    class HeaderViewHolder(private var binding: DetailedHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(recipe: Recipe) {
                binding.recipe = recipe
                binding.executePendingBindings()
                }
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DetailedHeaderBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    class StepViewHolder(private var binding: StepViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(step: Step) {
            binding.step = step
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): StepViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StepViewBinding.inflate(layoutInflater, parent, false)
                return StepViewHolder(binding)
            }
        }
    }

    class FooterViewHolder(private var binding: DetailedFooterBinding, val viewModel: DetailedRecipeViewModel) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(similarRecipes: Recipe) {
            binding.viewModel = viewModel
            binding.similarRecipes = similarRecipes
            // Forces the data binding to execute immediately,to correctly size RecyclerVieW
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, viewModel: DetailedRecipeViewModel): FooterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DetailedFooterBinding.inflate(layoutInflater, parent, false)
                return FooterViewHolder(binding, viewModel)
            }
        }
    }
}

/**************DEFINING DIFFCALLBACKS TO IMPROVE EFFICIENCY**********************/

class StepDiffCallback : DiffUtil.ItemCallback<DataItem>() {
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
    data class StepItem(val step: Step): DataItem() {
        override val id = step.number.toLong()
    }

    data class Header(val recipe: Recipe): DataItem() {
        override val id = recipe.id.toLong()
    }

    data class FooterItem(val recipeFooter: Recipe) : DataItem() {
        override val id = recipeFooter.id.toLong()
    }

    abstract val id: Long
}
