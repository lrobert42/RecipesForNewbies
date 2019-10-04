package com.example.recipesfornewbies.ingredientsDatabase

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface IngredientsDatabaseDAO {

    @Insert
    fun insert(ingredient: Ingredients)

    @Update
    fun update(ingredient: Ingredients)

    @Query("SELECT * from ingredient_table WHERE ingredient_name = :key")
    fun get(key: String): Ingredients?

    @Query("DELETE FROM ingredient_table")
    fun clear()

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient_name DESC")
    fun getAllIngredients(): LiveData<List<Ingredients>>

    @Query("SELECT * FROM ingredient_table ORDER BY ingredient_name DESC LIMIT 1")
    fun getToIngredient(): Ingredients?

    @Query("SELECT * from ingredient_table WHERE ingredient_name = :key")
    suspend fun getIngredientWithName(key: String): Ingredients?

    @Query("UPDATE ingredient_table SET ingredient_amount = 0 WHERE ingredient_name = :key ")
    fun resetIngredientsAmount(key: String)

    @Query("UPDATE ingredient_table SET ingredient_amount = ingredient_amount + 1  WHERE ingredient_name = :key ")
    fun addOneToAmount(key: String)

    @Query("UPDATE ingredient_table SET ingredient_amount = ingredient_amount - 1 WHERE ingredient_name = :key ")
    fun subtractOneFromAmount(key: String)
    
    @Query("SELECT * FROM ingredient_table WHERE ingredient_amount > 0")
    fun whatsInMyFridge() : List<Ingredients?>

    @Query("SELECT * FROM ingredient_table WHERE ingredient_name LIKE :key")
    fun autoCompleteSearchCursor(key: String) : Cursor

    @Query("UPDATE ingredient_table SET ingredient_amount = 0")
    fun clearItemsInFridge()

    @Query("DELETE FROM ingredient_table WHERE _id > 992")
    fun clearUserInput()

    @Query("UPDATE ingredient_table SET ingredient_amount = ingredient_amount + 1 WHERE _id = :key")
    fun addOneByID(key: Int)
}


