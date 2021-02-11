package com.example.factorynewsreaderapp.data.room

import android.content.Context
import androidx.room.Room
import com.example.factorynewsreaderapp.utilities.DATABASE_NAME

/**
 * The Room database for this app.
 */
class DatabaseClient private constructor(context: Context) {

    private val articlesDatabase: ArticlesDatabase =
        Room.databaseBuilder(context, ArticlesDatabase::class.java, DATABASE_NAME).build()

    fun getArticlesDatabase(): ArticlesDatabase {
        return articlesDatabase
    }

    companion object {
        @Volatile
        private var instance: DatabaseClient? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseClient? {
            if (instance == null) {
                instance = DatabaseClient(context)
            }
            return instance
        }
    }

}