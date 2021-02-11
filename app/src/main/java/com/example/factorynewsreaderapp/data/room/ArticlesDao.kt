package com.example.factorynewsreaderapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.factorynewsreaderapp.data.Article

/**
 * The Data Access Object for the Article class.
 */
@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<Article>)

    @Query("DELETE FROM articles_db")
    suspend fun clearAllArticles()

    @Query("SELECT * FROM articles_db")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT dataModified FROM articles_db")
    fun dataModifiedTime(): Long

}