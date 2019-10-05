package com.example.recipesfornewbies.groceryList

import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO
import kotlinx.coroutines.*

class GroceryListViewModel(dataSource: WishlistDatabaseDAO) : ViewModel() {

    val database = dataSource

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init{
        getWishlistRecipes()
    }
    private fun getWishlistRecipes(){
        coroutineScope.launch{
           val list =  withContext(Dispatchers.IO){
                val wishListRecipes = database.getAllRecipe()
                wishListRecipes
            }
        }
        TODO("Parse ingredients in recipe wishlist, sort them by aisle and print them")
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}




