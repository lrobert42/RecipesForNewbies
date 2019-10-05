package com.example.recipesfornewbies.UtilsClass

import androidx.room.TypeConverter
import com.example.recipesfornewbies.recipes.ExtendedIngredient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ExtendedIngredientsConverters {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val adapter = moshi.adapter<ExtendedIngredient>(ExtendedIngredient::class.java)

    @TypeConverter
    fun extendedIngredientsToJson(extendedIngredient: List<ExtendedIngredient?>): String {

            var finalString = ""
            for (i in 0..extendedIngredient.lastIndex){
                finalString += adapter.toJson(extendedIngredient[i])
                if (i != extendedIngredient.lastIndex) {
                    finalString += '\t'
                }
            }
        return finalString
    }

    @TypeConverter
    fun jsonToExtendedIngredients(json: String): List<ExtendedIngredient?> {

        val finalList = mutableListOf<ExtendedIngredient?>()
        val stringList = json.split('\t')
        for (i in 0 until (stringList.lastIndex)){
            finalList.add(adapter.fromJson(stringList[i]))
        }
        return finalList
    }
}