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

//@JsonClass(generateAdapter = true)
//data class Step(
//    val equipment: List<Any>?,
//    val ingredients: List<Any>?,
//    val length: Length?,
//    val number: Int = 0,
//    val step: String?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        equipment = emptyList<Any>(),
//        ingredients = emptyList<Any>(),
//        length = Length(),
//        number = 0,
//        step = ""
//    )
//}
//
//@JsonClass(generateAdapter = true)
//data class Length(
//    val number: Int,
//    val unit: String?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        number = 0,
//        unit = ""
//    )
//}