package com.example.recipesfornewbies.detailedrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesfornewbies.recipes.Recipe

class DetailedRecipeViewModelFactory(
    private val recipe: Recipe
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailedRecipeViewModel::class.java)) {
            return DetailedRecipeViewModel(recipe) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}