//package com.example.recipesfornewbies.recipes
//
//import com.squareup.moshi.JsonClass
//
//@JsonClass(generateAdapter = true)
//data class DetailedRecipe(
//    var cuisines: List<Any>?,
//    var diets: List<Any>?,
//    var dishTypes: List<String>?,
//    var extendedIngredients: List<ExtendedIngredient>?,
//    var id: Int,
//    var image: String?,
//    var imageType: String?,
//    var instructions: String?,
//    var occasions: List<Any>?,
//    var pricePerServing: Double,
//    var readyInMinutes: Int,
//    var servings: Int,
//    var sourceName: String?,
//    var sourceUrl: String?,
//    var spoonacularScore: Double,
//    var spoonacularSourceUrl: String?,
//    var title: String?,
//    var winePairing: WinePairing?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        cuisines  =  emptyList(),
//        diets = emptyList(),
//        dishTypes = emptyList(),
//        extendedIngredients = emptyList<ExtendedIngredient>(),
//        id = 0,
//        image = "",
//        imageType="",
//        instructions="",
//        occasions = emptyList(),
//        pricePerServing = 0.0,
//        readyInMinutes=0,
//        servings= 0,
//        sourceName = "",
//        sourceUrl= "",
//        spoonacularScore= 0.0,
//        spoonacularSourceUrl= "",
//        title = "",
//        winePairing = WinePairing()
//    )
//}
//
//@JsonClass(generateAdapter = true)
//data class ExtendedIngredient(
//    var aisle: String?,
//    var amount: Double,
//    var consitency: String?,
//    var id: Int,
//    var image: String?,
//    var measures: Measures,
//    var meta: List<String>?,
//    var metaInformation: List<String>?,
//    var name: String?,
//    var original: String?,
//    var originalName: String?,
//    var originalString: String?,
//    var unit: String?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        aisle = "",
//        amount = 0.0,
//        consitency = "",
//        id = 0,
//        image = "",
//        measures = Measures(),
//        meta = emptyList<String>(),
//        metaInformation = emptyList<String>(),
//        name = "",
//        original = "",
//        originalName = "",
//        originalString = "",
//        unit = ""
//    )
//}
//
//@JsonClass(generateAdapter = true)
//data class Measures(
//    var metric: Metric?,
//    var us: Us?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        metric = Metric(),
//        us = Us()
//    )
//}
//
//@JsonClass(generateAdapter = true)
//data class Metric(
//    var amount: Double,
//    var unitLong: String,
//    var unitShort: String
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        amount = 0.0,
//        unitLong = "",
//        unitShort = ""
//    )
//}
//
//@JsonClass(generateAdapter = true)
//data class Us(
//    var amount: Double,
//    var unitLong: String?,
//    var unitShort: String?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        amount = 0.0,
//        unitShort = "",
//        unitLong = ""
//    )
//}
//
//@JsonClass(generateAdapter = true)
//data class WinePairing(
//    var pairedWines: List<Any>?,
//    var pairingText: String?,
//    var productMatches: List<Any>?
//){
//    @SuppressWarnings("unused")
//    constructor() : this(
//        pairedWines = emptyList(),
//        pairingText = "",
//        productMatches =  emptyList()
//    )
//}