package com.example.recipesfornewbies.groceryList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO

class GroceryListViewModelFactory(
    private val dataSource: WishlistDatabaseDAO) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GroceryListViewModel::class.java)) {
                return GroceryListViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}