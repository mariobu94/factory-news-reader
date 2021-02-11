package com.example.factorynewsreaderapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.factorynewsreaderapp.data.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}