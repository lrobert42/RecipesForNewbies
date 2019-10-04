package com.example.recipesfornewbies.whatsinmyfridge

import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipesfornewbies.ingredientsDatabase.Ingredients
import com.example.recipesfornewbies.ingredientsDatabase.IngredientsDatabaseDAO
import kotlinx.coroutines.*

class WhatsInMyFridgeViewModel(datasource: IngredientsDatabaseDAO) : ViewModel() {

    private val database = datasource

    private var _ingredients = MutableLiveData<List<Ingredients?>>()
    val ingredients : LiveData<List<Ingredients?>>
    get() = _ingredients


    private val _suggestion = MutableLiveData<Cursor>()
    val suggestion: LiveData<Cursor>
        get() = _suggestion

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        updateIngredientList()
        if (_ingredients.value.isNullOrEmpty()){_ingredients.value = emptyList()}
        //onAddIngredients()
        }


    private fun updateIngredientList(){
        coroutineScope.launch {
            val _databaseResult = getIngredientListFromDatabase()
            try {
                _ingredients.value = _databaseResult

            }
            catch (e : Exception)
            {
                Log.e("VM WIMF", "Error: $e")
            }
        }
    }

    private suspend fun getIngredientListFromDatabase() : List<Ingredients?>{
         return withContext(Dispatchers.IO){
            val ingredientList = database.whatsInMyFridge()
             ingredientList
         }
    }

    fun onAddIngredients(query: String){
        coroutineScope.launch {
          //  add("almonds")
            add(query)
        }
        updateIngredientList()
    }

    private suspend fun add(query: String) {
        withContext(Dispatchers.IO) {
            database.addOneToAmount(query)
        }
    }


    fun onSubtractIngredients(query: String){
        coroutineScope.launch {
            subtract(query)
        }
        updateIngredientList()
    }

    private suspend fun subtract(query: String){
        withContext(Dispatchers.IO)
        {
            database.subtractOneFromAmount(query)
        }
    }

    private val _navigateToSearchFragment = MutableLiveData<Boolean>()
    val navigateToSearchFragment : LiveData<Boolean>
    get() = _navigateToSearchFragment

    fun onShowRecipesClicked(){
        _navigateToSearchFragment.value = true
    }

    fun onDoneSearching(){
        _navigateToSearchFragment.value = false
    }


    private val _showSearchButton = MutableLiveData<Boolean>()
    val showSearchButton : LiveData<Boolean>
    get() = _showSearchButton

    fun showSearchButton(){
        _showSearchButton.value = true
    }

    fun hideSearchButton(){
        _showSearchButton.value = false
    }



    /********HANDLING SEARCH VIEW FUNCTIONS ********/
    fun onSuggestionClicked(id: Int){
        coroutineScope.launch{
            addOneByID(id)
        }
        updateIngredientList()
    }

    private suspend fun addOneByID(id: Int) {
         withContext(Dispatchers.IO) {
         database.addOneByID(id)
        }
    }


    private val _databaseName = MutableLiveData<Ingredients>()
    val databaseName : LiveData<Ingredients>
        get() = _databaseName

    fun onQuerySubmit(query: String) {

        coroutineScope.launch{
            getName(query)
            Log.i("VM", "${_databaseName.value}")
            _databaseName.value?.let {

                if (query.toLowerCase() == it.ingredient_name.toLowerCase()) {
                   onAddIngredients(query.toLowerCase())
                }

            }?: withContext(Dispatchers.IO) {
                database.insert(Ingredients(query, 0, 1))
                }
        }
         _databaseName.value = null
         updateIngredientList()
    }


    private suspend fun getName(query: String)  {

        try {
            _databaseName.value = withContext(Dispatchers.IO) {
                val _nameFromDB = database.getIngredientWithName(query)
                _nameFromDB
            }
        }
        catch (e : Exception) {
            Log.e("VM WIMF", "Error: $e")
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



    fun getNameFromDB(query: String) {
        coroutineScope.launch{
            try {
                _suggestion.value = withContext(Dispatchers.IO){

                    val _cursorFromDB = database.autoCompleteSearchCursor("%$query%")
                    _cursorFromDB
                    }
            }
            catch (e : Exception) {
                Log.e("VM WIMF", "Error: $e")
            }
        }
    }

    fun onButtonClearClicked(){
        coroutineScope.launch{
            withContext(Dispatchers.IO){
                database.clearUserInput()
            }
        }
        updateIngredientList()
    }
}