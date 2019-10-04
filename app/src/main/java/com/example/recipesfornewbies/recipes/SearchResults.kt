package com.example.recipesfornewbies.recipes

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResults(
    val id: Int,
    val image: String?,
    val imageType: String?,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<MissedIngredient?>,
    val title: String?,
    val unusedIngredients: List<UnusedIngredient?>,
    val usedIngredientCount: Int,
    val usedIngredients: List<Any?>
)

@JsonClass(generateAdapter = true)
data class MissedIngredient(
    val aisle: String?,
    val amount: Double,
    val extendedName: String?,
    val id: Int,
    val image: String?,
    val metaInformation: List<String?>,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val originalString: String?,
    val unit: String?,
    val unitLong: String?,
    val unitShort: String?
)

@JsonClass(generateAdapter = true)
data class UnusedIngredient(
    val aisle: String?,
    val amount: Double,
    val id: Int,
    val image: String?,
    val metaInformation: List<Any?>,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val originalString: String?,
    val unit: String?,
    val unitLong: String?,
    val unitShort: String?
)