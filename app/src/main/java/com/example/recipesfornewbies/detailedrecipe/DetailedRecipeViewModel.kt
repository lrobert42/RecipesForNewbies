package com.example.recipesfornewbies.detailedrecipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.network.service
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.recipes.Step
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class DetailedRecipeViewModel(currentRecipe : Recipe) : ViewModel() {

    private val _instructions = MutableLiveData<List<Step>>()
    val instructions: LiveData<List<Step>>
        get() = _instructions


    private val _recipe = MutableLiveData<Recipe>()
    val recipe : LiveData<Recipe>
    get() = _recipe

    private val _navigateTo = MutableLiveData<Recipe>()
    val navigateTo : LiveData<Recipe>
    get() = _navigateTo

    private val _doneNavigating = MutableLiveData<Boolean>()
    val doneNavigating : LiveData<Boolean>
        get() = _doneNavigating

    private val _similarRecipes = MutableLiveData<List<Recipe>>()
    val similarRecipes : LiveData<List<Recipe>>
        get() = _similarRecipes


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
      //  _navigateTo.value = Recipe(0,"","",0, 0)
        if (currentRecipe.id > 0){
            _recipe.value = currentRecipe
            getRecipeInstructions(currentRecipe.id)
            getSimilarRecipes(currentRecipe.id)

        } else {
            Log.e("ViewModel Detailed", "Error: no argument passed to View Model.")
        }
    }
    private fun getRecipeInstructions(id: Int){
        coroutineScope.launch {
            val getInstructions = service.getInstructions(id.toString()).await()
            try {
                 _instructions.value = getInstructions.first().stepList
            } catch (e: Exception) {
                Log.i("ViewModel", "Error: $e")
            }
        }
    }

    private fun getSimilarRecipes(id: Int){
        coroutineScope.launch {
            val getSimilar = service.getSimilarRecipes(id.toString()).await()
            try {
                _similarRecipes.value = getSimilar
            } catch (e: Exception) {
                Log.i("ViewModel", "Error: $e")
            }
        }
    }

    fun onSimilarRecipeClicked(similarRecipeClicked : Recipe){
        _navigateTo.value = similarRecipeClicked
    }
}