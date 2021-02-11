package com.example.factorynewsreaderapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.factorynewsreaderapp.utilities.DATABASE_NAME
import java.io.Serializable

@Entity(tableName = DATABASE_NAME)
data class Article(
    val author: String,
    val description: String,
    val publishedAt: String?,
    @PrimaryKey
    val title: String,
    val url: String,
    val urlToImage: String,
    var dataModified: Long
) : Serializable
