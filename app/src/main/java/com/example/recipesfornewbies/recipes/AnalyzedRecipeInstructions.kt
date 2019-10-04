package com.example.recipesfornewbies.recipes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AnalyzedRecipeInstructions(
    val name: String?,
   @Json(name = "steps") val stepList: List<Step>
) {
    @SuppressWarnings("unused")
    constructor() : this (
        name = "",
        stepList = emptyList()
    )
}