package com.example.recipesfornewbies.wishlistDatabase

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WishlistDatabaseDAO {

    @Insert
    fun insert(recipe: WishlistRecipe)

    @Update
    fun update(ingredient: WishlistRecipe)

    @Query("SELECT * from wishlist_table WHERE recipe_title = :key")
    fun get(key: String): WishlistRecipe?

    @Query("DELETE FROM wishlist_table")
    fun clear()

    @Query("SELECT * FROM wishlist_table ORDER BY recipe_title DESC")
    fun getAllRecipe(): List<WishlistRecipe?>

    @Query("SELECT * FROM wishlist_table ORDER BY recipe_title DESC LIMIT 1")
    fun getToIngredient(): WishlistRecipe?

    @Query("SELECT * from wishlist_table WHERE recipe_title = :key")
    suspend fun getRecipeWithName(key: String): WishlistRecipe?

    @Query("SELECT * FROM wishlist_table WHERE recipe_title LIKE :key")
    fun autoCompleteSearchCursor(key: String) : Cursor

    @Query("DELETE FROM wishlist_table WHERE _id = :key")
    fun clearRecipe(key: Int)


}