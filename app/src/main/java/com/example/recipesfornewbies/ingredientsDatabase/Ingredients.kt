package com.example.recipesfornewbies.ingredientsDatabase

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ingredient_table")
data class Ingredients(
    @ColumnInfo(name = "ingredient_name")
    var ingredient_name: String = "",

    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0,

    @ColumnInfo(name = "ingredient_amount")
    var ingredient_amount: Int = 0
) : Parcelable