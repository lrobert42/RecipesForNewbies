package com.example.recipesfornewbies

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipesfornewbies.defaultrecipelist.RandomRecipeListAdapter
import com.example.recipesfornewbies.defaultrecipelist.SearchResultsAdapter
import com.example.recipesfornewbies.detailedrecipe.DetailedRecipeAdapter
import com.example.recipesfornewbies.ingredientsDatabase.Ingredients
import com.example.recipesfornewbies.recipes.Recipe
import com.example.recipesfornewbies.recipes.SearchResults
import com.example.recipesfornewbies.recipes.Step
import com.example.recipesfornewbies.whatsinmyfridge.WhatsInMyFridgeAdapter


/**DEFAULT RECIPE LIST FRAGMENT BINDING ADAPTERS**/


@BindingAdapter("defaultListData", "emptyTextView")
fun bindRecipeRecyclerView(recyclerView: RecyclerView, defaultListData: List<Recipe>?, emptyTextView: TextView) {

    if(defaultListData.isNullOrEmpty())
    {
        recyclerView.isVisible = false
        emptyTextView.isVisible = true
    } else{
        recyclerView.isVisible = true
        emptyTextView.isVisible = false
        val adapter = recyclerView.adapter as RandomRecipeListAdapter
        adapter.submitList(defaultListData)
    }
}

@BindingAdapter("imageFromUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let{
        val baseUri = "https://spoonacular.com/recipeImages/"
        val completeURL = if(imgUrl.contains("https://spoonacular.com/recipeImages/")) {
                imgUrl
            }
        else {
            baseUri+imgUrl
        }
        val imgUri = completeURL.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .override(600, 400)
                    .centerCrop()
            )
            .into(imgView)
    }?: Log.e("BindingAdapters", "Image URL is empty!")
}

/**DETAILED RECIPE FRAGMENT BINDING ADAPTERS**/


@BindingAdapter ("readyInMinutes")
fun printReadyInMinutes(textView: TextView, minutes: Int) {
    textView.text = "Ready in $minutes minutes"
}

@BindingAdapter("servings")
fun printServings(textView: TextView, servings: Int){
    textView.text = "For $servings servings"
}

@BindingAdapter("instructionList", "recipe", "similarRecipes")
fun bindDetailedRecyclerView(recyclerView: RecyclerView, stepList :List<Step>?, recipe: Recipe, similarRecipes : List<Recipe>?){
    val adapter = recyclerView.adapter as DetailedRecipeAdapter
    adapter.parseAndSubmitList(recipe, stepList, similarRecipes)
}

/**DETAILED RECIPE FRAGMENT'S RECYCLER VIEW BINDING ADAPTERS**/

@BindingAdapter("printStepNumber")
fun printStepNumbers(textView: TextView, item: Int){
    textView.text = "Step number $item"
}


/** WHAT'S IN MY FRIDGE FRAGMENT **/
@BindingAdapter( "itemsInFridge", "emptyTextView")
fun bindFridgeListRecyclerView(recyclerView: RecyclerView,itemsInFridge: List<Ingredients?>,  emptyTextView: TextView ){

   // val textView : TextView = findViewById(R.id.empty_view)
    if (itemsInFridge.isEmpty()){
        recyclerView.isVisible = false
        emptyTextView.isVisible = true

    }
    else {
        recyclerView.isVisible = true
        emptyTextView.isVisible = false
        val adapter = recyclerView.adapter as WhatsInMyFridgeAdapter
        adapter.submitList(itemsInFridge)
    }
}

@BindingAdapter("intToString")
fun intToString(view: TextView, number: Int){
    view.text = number.toString()
}

@BindingAdapter("searchListData")
fun submitSearchResults(view: RecyclerView, list: List<SearchResults?>) {
    val adapter = view.adapter as SearchResultsAdapter
    adapter.submitList(list)
}

@BindingAdapter("capitalize")
fun capitalizeText(view: TextView, string: String){
    view.text = string.capitalize()
}

@BindingAdapter("wishlistData", "emptyTextView")
fun bindWishlistData(recyclerView: RecyclerView, wishlistData: List<Recipe>?, emptyTextView: TextView) {

//    if(wishlistData.isNullOrEmpty()) {
//        recyclerView.isVisible = false
//        emptyTextView.isVisible = true
//    } else {
        recyclerView.isVisible = true
        emptyTextView.isVisible = false
        val adapter = recyclerView.adapter as RandomRecipeListAdapter
        adapter.submitList(wishlistData)
//    }
}



///**OTHER BINDING ADAPTERS **/
//@BindingAdapter("assertedText")
//fun printAssertedText(textView: TextView, text: String?){
//
//    text?.let{
//        textView.text = it
//    }?: (textView.text = "Something went wrong!")
//}