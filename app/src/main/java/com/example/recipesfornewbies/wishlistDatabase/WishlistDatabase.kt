package com.example.recipesfornewbies.wishlistDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WishlistRecipe::class], version = 1, exportSchema = false)
abstract class WishlistDatabase : RoomDatabase() {

    abstract val wishlistDatabaseDAO: WishlistDatabaseDAO

    companion object {
        @Volatile
        private var INSTANCE: WishlistDatabase? = null

        fun getInstance(context: Context): WishlistDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {
                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        WishlistDatabase::class.java,
                        "wishlist_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }
}

