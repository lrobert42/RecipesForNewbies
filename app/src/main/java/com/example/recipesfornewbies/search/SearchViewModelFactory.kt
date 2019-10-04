package com.example.recipesfornewbies.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesfornewbies.ingredientsDatabase.Ingredients

class SearchViewModelFactory(
    private val ingredients: Array<Ingredients>
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(ingredients) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}