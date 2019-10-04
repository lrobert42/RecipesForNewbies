package com.example.recipesfornewbies.defaultrecipelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.network.service
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.wishlistDatabase.WishlistDatabaseDAO
import com.example.recipesfornewbies.wishlistDatabase.WishlistRecipe
import kotlinx.coroutines.*


class DefaultRecipeListViewModel(datasource: WishlistDatabaseDAO) : ViewModel() {

//    private val _recipeList = MutableLiveData<List<Recipe>>()
//    val recipeList: LiveData<List<Recipe>>
//    get() = _recipeList

    val database = datasource


    private val _randomRecipeList = MutableLiveData<List<Recipe>>()
    val randomRecipeList : LiveData<List<Recipe>>
    get() = _randomRecipeList

    //Defining coroutine
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Log.i("VM", "Datasource: $database")
      //  _randomRecipeList.value = emptyList()
        getRandomRecipes()
    }

//    fun getRecipesForNewbie() {
//        coroutineScope.launch {
//            var getRecipes = service.getRecipe().await()
//            try {
//                _recipeList.value = getRecipes.results
//            } catch (e: Exception) {
//                Log.i("ViewModel","Error: $e")
//            }
//        }
//    }

    fun updateRecipeList(){
        getRandomRecipes()
    }


    var cleanRecipeList = mutableListOf<Recipe>()

    private fun cleanRandomRecipes(dirtyRecipeList : List<Recipe>){

        Log.i("VM", "Je passe dans clean")
        Log.i("VM", "cleanList: $cleanRecipeList")

        cleanRecipeList.addAll(dirtyRecipeList.filter{
            Log.i("VM", "image: ${it.image}")
             it.image != null && it.image.isNotEmpty()
            })

        if(cleanRecipeList.size >= 3){
            _randomRecipeList.value = cleanRecipeList
        } else
        {
            getRandomRecipes()
        }
    }

    private fun getRandomRecipes() {
        coroutineScope.launch {
            try {
                val getRecipes = service.getRandomRecipes().await().recipes
                cleanRandomRecipes(getRecipes)
            } catch (e: Exception) {
                Log.e("ViewModel","Error: $e")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private val _showToast = MutableLiveData<Boolean>()
    val showToast : LiveData<Boolean>
    get() = _showToast


    fun onToastShown(){
        _showToast.value = false
    }

    fun onSwipeWishList(position: Int){
        coroutineScope.launch{
            val recipe = _randomRecipeList.value!![position]
            if (!recipeIsInWishlist(recipe)){
                Log.i("VM", "Je vais insert")
                insertRecipeInWishlist(recipe)
            }
            _showToast.value = true
        }
    }

    private suspend fun debugsarace(){
        withContext(Dispatchers.IO)
        {
            val coucou = database.getAllRecipe()
            Log.i("VM", "coucou: $coucou")

        }
    }

    private suspend fun insertRecipeInWishlist(recipe: Recipe){

        withContext(Dispatchers.IO){
            val newWishlistRecipe =
                WishlistRecipe(_id = recipe.id,
                    recipe_image = recipe.image?: "",
                    recipe_ready_in_minutes = recipe.readyInMinutes,
                    recipe_servings = recipe.servings,
                    recipe_title = recipe.title?: ""
                )
            Log.i("VM", "Voici la nouvelle recette en wishList: $newWishlistRecipe")
            database.insert(newWishlistRecipe)
        }
    }

    private suspend fun recipeIsInWishlist(recipe: Recipe): Boolean{
        return withContext(Dispatchers.IO){
            try {
                recipe.title?.let {
                    val databaseRecipe = database.getRecipeWithName(it)
                    return@withContext (areRecipesTheSame(recipe, databaseRecipe))
                }
                false
            } catch (e: Exception){
                Log.e("VM", "Error: $e")
                false
            }
        }
    }

    private fun areRecipesTheSame(recipe: Recipe, databaseRecipe: WishlistRecipe?) : Boolean {

        databaseRecipe?.let{
            if(recipe.title == it.recipe_title &&
                recipe.image == it.recipe_image &&
                recipe.servings == it.recipe_servings &&
                recipe.readyInMinutes == it.recipe_ready_in_minutes &&
                recipe.id == it._id) {
            return@areRecipesTheSame true
            }
        }
        return false
    }

    fun remove(position: Int){
        coroutineScope.launch {
            _randomRecipeList.value?.let{
                var newRecipeList: MutableList<Recipe> = _randomRecipeList.value as MutableList<Recipe>
                newRecipeList.removeAt(position)
                _randomRecipeList.value = newRecipeList

                }
            }
        updateRecipeList()
    }


    fun onClickDontShowThis(){
        TODO("Implement a 'don't show me this'")
    }
}


