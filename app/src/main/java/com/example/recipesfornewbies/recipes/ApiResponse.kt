package com.example.recipesfornewbies.recipes

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    var offset: Int,
    var number: Int,
    var results : List<Recipe>
){
    @SuppressWarnings("unused")
    constructor() : this(
        offset = 0,
        number = 0,
        results = emptyList<Recipe>()
    )
}

