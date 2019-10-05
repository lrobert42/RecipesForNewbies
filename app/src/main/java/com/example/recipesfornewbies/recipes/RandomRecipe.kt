package com.example.recipesfornewbies.recipes

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@JsonClass(generateAdapter = true)
data class RandomRecipe(
    val recipes: List<Recipe> = emptyList()
)
{ constructor() : this(
        recipes= emptyList()
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Recipe(
    val aggregateLikes: Int = 0,
    val analyzedInstructions: List<AnalyzedInstruction> = emptyList(),
    val cheap: Boolean?,
    val cookingMinutes: Int = 0,
    val creditsText: String?,
    val cuisines: @RawValue List<Any> = emptyList(),
    val dairyFree: Boolean?,
    val diets: @RawValue List<Any> = emptyList(),
    val dishTypes: List<String?> = emptyList(),
    val extendedIngredients: List<ExtendedIngredient?> = emptyList(),
    val gaps: String?,
    val glutenFree: Boolean?,
    val healthScore: Double?,
    val id: Int = 0,
    val image: String?,
    val imageType: String?,
    val instructions: String?,
    val ketogenic: Boolean?,
    val lowFodmap: Boolean?,
    val occasions: @RawValue List<Any> = emptyList(),
    val preparationMinutes: Int = 0,
    val pricePerServing: Double?,
    val readyInMinutes: Int = 0,
    val servings: Int = 0,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularScore: Double?,
    val spoonacularSourceUrl: String?,
    val sustainable: Boolean?,
    val title: String?,
    val vegan: Boolean?,
    val vegetarian: Boolean?,
    val veryHealthy: Boolean?,
    val veryPopular: Boolean?,
    val weightWatcherSmartPoints: Int = 0,
    val whole30: Boolean?,
    val winePairing: WinePairing?
): Parcelable {
    @SuppressWarnings("unused")
    constructor() : this(
        aggregateLikes = 0,
        analyzedInstructions = emptyList(),
        cheap = false,
        cookingMinutes = 0,
        creditsText = "",
        cuisines = emptyList(),
        dairyFree = false,
        diets = emptyList(),
        dishTypes = emptyList(),
        extendedIngredients = emptyList(),
        gaps = "",
        glutenFree = false,
        healthScore = 0.0,
        id = 0,
        image = "",
        imageType = "",
        instructions = "",
        ketogenic = false,
        lowFodmap = false,
        occasions = emptyList(),
        preparationMinutes = 0,
        pricePerServing = 0.0,
        readyInMinutes = 0,
        servings = 0,
        sourceName = "",
        sourceUrl = "",
        spoonacularScore = 0.0,
        spoonacularSourceUrl = "",
        sustainable = false,
        title = "",
        vegan = false,
        vegetarian = false,
        veryHealthy = false,
        veryPopular = false,
        weightWatcherSmartPoints = 0,
        whole30 = false,
        winePairing = WinePairing()
    )

    constructor(id: Int, title: String?, image: String?, servings: Int, readyInMinutes: Int, extendedIngredients: List<ExtendedIngredient?>) : this(
        aggregateLikes = 0,
        analyzedInstructions = emptyList(),
        cheap = false,
        cookingMinutes = 0,
        creditsText = "",
        cuisines = emptyList(),
        dairyFree = false,
        diets = emptyList(),
        dishTypes = emptyList(),
        extendedIngredients = extendedIngredients,
        gaps = "",
        glutenFree = false,
        healthScore = 0.0,
        id = id,
        image = image,
        imageType = "",
        instructions = "",
        ketogenic = false,
        lowFodmap = false,
        occasions = emptyList(),
        preparationMinutes = 0,
        pricePerServing = 0.0,
        readyInMinutes = readyInMinutes,
        servings = servings,
        sourceName = "",
        sourceUrl = "",
        spoonacularScore = 0.0,
        spoonacularSourceUrl = "",
        sustainable = false,
        title = title,
        vegan = false,
        vegetarian = false,
        veryHealthy = false,
        veryPopular = false,
        weightWatcherSmartPoints = 0,
        whole30 = false,
        winePairing = WinePairing()
    )
}


@Parcelize
@JsonClass(generateAdapter = true)
data class AnalyzedInstruction(
    val name: String?,
    val steps: List<Step>?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        name = "",
        steps = emptyList()
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Step(
    val equipment: @RawValue List<Any> = emptyList(),
    val ingredients: @RawValue List<Any> = emptyList(),
    val length: Length?,
    val number: Int = 0,
    val step: String?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        equipment = emptyList<Any>(),
        ingredients = emptyList<Any>(),
        length = Length(),
        number = 0,
        step = ""
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Length(
    val number: Int = 0,
    val unit: String?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        number = 0,
        unit = ""
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class ExtendedIngredient(
    val aisle: String?,
    val amount: Double?,
    val consitency: String?,
    val id: Int? = 0,
    val image: String?,
    val measures: Measures,
    val meta: @RawValue List<Any> = emptyList(),
    val metaInformation: @RawValue List<Any> = emptyList(),
    val name: String?,
    val original: String?,
    val originalName: String?,
    val originalString: String?,
    val unit: String?
) : Parcelable {
    @SuppressWarnings("unused")
    constructor() : this(
        aisle = "",
        amount = 0.0,
        consitency = "",
        id = 0,
        image = "",
        measures = Measures(),
        meta = emptyList<Any>(),
        metaInformation = emptyList<Any>(),
        name = "",
        original = "",
        originalName = "",
        originalString = "",
        unit = ""
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Measures(
    val metric: Metric?,
    val us: Us?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        metric = Metric(),
        us = Us()
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Metric(
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        amount = 0.0,
        unitLong = "",
        unitShort = ""
    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Us(
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        amount= 0.0,
        unitShort = "",
        unitLong = ""

    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class WinePairing(
    val pairedWines: List<String?> = emptyList(),
    val pairingText: String? = "",
    val productMatches: List<ProductMatche> = emptyList()

): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        pairedWines = emptyList(),
        pairingText = "",
        productMatches = emptyList()

    )
}

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductMatche(
    val averageRating: Double?,
    val description: String?,
    val id: Int = 0,
    val imageUrl: String?,
    val link: String?,
    val price: String?,
    val ratingCount: Double?,
    val score: Double?,
    val title: String?
): Parcelable{
    @SuppressWarnings("unused")
    constructor() : this(
        averageRating = 0.0,
        description = "",
        id = 0,
        imageUrl = "",
        link = "",
        price = "",
        ratingCount = 0.0,
        score = 0.0,
        title = ""
    )
}