package com.example.recipesfornewbies.groceryList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.recipes.ExtendedIngredient
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO
import com.example.recipesfornewbies.wishlistDatabase.WishlistRecipe
import kotlinx.coroutines.*

class GroceryListViewModel(dataSource: WishlistDatabaseDAO) : ViewModel() {

    val database = dataSource

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _ingredientList = MutableLiveData<List<ExtendedIngredient?>>()
    val ingredientList : LiveData<List<ExtendedIngredient?>>
    get() = _ingredientList


    init{
        getIngredientList()
    }
    private fun getIngredientList(){
        coroutineScope.launch{
            val sortedIngredient = ingredientListCreator(withContext(Dispatchers.IO){
                val wishListRecipes = database.getAllRecipe()
                wishListRecipes
            })
            try {
                _ingredientList.value = sortedIngredient
            } catch (e: Exception) {
                Log.e("VM", "Error parsing ingredients: $e")
            }
        }
    }

    private fun ingredientListCreator(wishListRecipes: List<WishlistRecipe?>) : List<ExtendedIngredient?>{
        val ingredientList = mutableListOf<ExtendedIngredient>()

        wishListRecipes.forEach{recipe ->
            recipe?.recipe_ingredients_needed?.forEach {ingredient ->
                if (ingredientList.contains(ingredient)){
                    ingredientList.indexOf(ingredient).plus(ingredient?.amount ?: 0.0)
                } else {
                    ingredientList.add(ingredient!!)
                }
            }
        }
        ingredientList.let{
            it.sortBy {
                it.aisle
            }
        }
    return ingredientList
    }

    fun onButtonClicked(){


    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}




