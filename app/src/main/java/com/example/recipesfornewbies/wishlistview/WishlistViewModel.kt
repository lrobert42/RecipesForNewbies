package com.example.recipesfornewbies.wishlistview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO
import kotlinx.coroutines.*

class WishlistViewModel(datasource: WishlistDatabaseDAO) : ViewModel(){

    val database = datasource

    //Defining coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _wishlist = MutableLiveData<List<Recipe?>>()
    val wishlist : LiveData<List<Recipe?>>
    get() = _wishlist

    init {
        getWishListRecipe()
    }

    fun remove(position: Int){
        coroutineScope.launch {
            _wishlist.value!![position]?.let {
                withContext(Dispatchers.IO){
                    database.clearRecipe(it.id)
                }
            }
            getWishListRecipe()
        }
    }

    private fun getWishListRecipe(){
        coroutineScope.launch {
            _wishlist.value = withContext(Dispatchers.IO){
                try{
                    val recipeList = database.getAllRecipe().map {
                        it?.let {
                            Recipe(id = it._id,
                                title = it.recipe_title,
                                image = it.recipe_image,
                                servings = it.recipe_servings,
                                readyInMinutes = it.recipe_ready_in_minutes,
                                extendedIngredients = it.recipe_ingredients_needed
                            )
                        }
                    }
                    recipeList
                }
                catch (e: Exception)
                {
                    Log.e("VM", "Error in getAllRecipes: $e")
                    null
                }
            }
        }
    }
    fun onButtonClicked(){
        coroutineScope.launch{
            withContext(Dispatchers.IO)
            {
                database.clear()
            }
        }
    }

}