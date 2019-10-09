package com.example.recipesfornewbies.network

import com.example.recipesfornewbies.recipes.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap


private const val BASE_URL = "https://api.spoonacular.com/recipes/"
private const val API_KEY = "e7b55905cce641b8bfb8cf86028cdb8e"
private const val apiPhrase = "apiKey=${API_KEY}"

interface RecipesService
{
    @GET("{id}/information?$apiPhrase")
    fun getRecipeById(@Path("id") id: String) : Deferred<Recipe>

   @GET("{id}/analyzedInstructions?$apiPhrase")
    fun getInstructions(@Path("id") id:String) : Deferred<List<AnalyzedRecipeInstructions>>

    @GET("{id}/similar?$apiPhrase&number=3")
    fun getSimilarRecipes(@Path("id")id: String) : Deferred<List<Recipe>>

    @GET("findByIngredients?")
    fun getRecipesByIngredients(@QueryMap info: Map<String, String>): Deferred<List<SearchResults>>

    @GET("random?$apiPhrase&number=3")
    fun getRandomRecipes() : Deferred<RandomRecipe>

    @GET("complexSearch?")
    fun getSearchResults(@QueryMap info: Map<String, String>) : Deferred<ApiResponse>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

val service by lazy{
    retrofit.create(RecipesService::class.java)
}
