package com.example.recipesfornewbies.whatsinmyfridge


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesfornewbies.ingredientsDatabase.IngredientsDatabaseDAO


class WhatsInMyFridgeViewModelFactory(
    private val dataSource: IngredientsDatabaseDAO) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WhatsInMyFridgeViewModel::class.java)) {
            return WhatsInMyFridgeViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
