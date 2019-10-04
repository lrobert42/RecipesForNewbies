package com.example.recipesfornewbies.defaultrecipelist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO


class DefaultRecipeViewModelFactory(
    private val dataSource: WishlistDatabaseDAO) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DefaultRecipeListViewModel::class.java)) {
            return DefaultRecipeListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
