package com.example.recipesfornewbies.wishlistDatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "wishlist_table")
data class WishlistRecipe(
    @ColumnInfo(name = "recipe_title")
    var recipe_title: String = "",

    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,

    @ColumnInfo(name="recipe_image")
    var recipe_image:String = "",

    @ColumnInfo(name="recipe_servings")
    var recipe_servings:Int = 0,

    @ColumnInfo(name="recipe_ready_in_minutes")
    var recipe_ready_in_minutes:Int = 0
) : Parcelable