package com.example.recipesfornewbies.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.ingredientsDatabase.Ingredients
import com.example.recipesfornewbies.network.service
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.recipes.SearchResults
import kotlinx.coroutines.*


class SearchViewModel(args : Array<Ingredients>) : ViewModel() {



    private val _searchResultsList = MutableLiveData<List<SearchResults>>()
    val searchResultsList: LiveData<List<SearchResults>>
        get() = _searchResultsList

    private val _newRecipe = MutableLiveData<Recipe>()
    val newRecipe : LiveData<Recipe>
    get() = _newRecipe


    private val ingredientList = args


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _searchResultsList.value = emptyList()
        findRecipesByIngredients()
    }



    private fun parseIngredients(ingredientList: Array<Ingredients>) : Map<String, String>{

        var finalString = ""
        for (i in (0..ingredientList.lastIndex)){
           finalString += ingredientList[i].ingredient_name
            if (i != ingredientList.lastIndex) finalString += ",+"
        }


        val queryMap : Map<String, String> = mapOf(
            "ingredients" to finalString,
            "number" to "3",
            "apiKey" to "e7b55905cce641b8bfb8cf86028cdb8e")

        println(queryMap)

        return queryMap

    }
    private fun findRecipesByIngredients(){
        coroutineScope.launch {
            val getRecipes = service.getRecipesByIngredients(parseIngredients(ingredientList)).await()
            try {
                _searchResultsList.value = getRecipes
            } catch (e: Exception) {
                Log.e("ViewModel", "Error: $e")
            }
        }
    }

    fun getRecipeByID(id: Int)  {
        coroutineScope.launch {
            _newRecipe.value =  requestRecipeByID(id)
        }
    }

    private suspend fun requestRecipeByID(id : Int) : Recipe{
          return withContext(Dispatchers.IO){
                val getRecipes = service.getRecipeById(id.toString()).await()
                    val recipeClicked = Recipe(getRecipes.id,
                        getRecipes.title,
                        getRecipes.image,
                        getRecipes.readyInMinutes,
                        getRecipes.servings,
                        getRecipes.extendedIngredients)

              recipeClicked
            }
        }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}