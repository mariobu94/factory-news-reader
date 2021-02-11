package com.example.factorynewsreaderapp.data

import java.io.Serializable

data class Articles(
    val articles: ArrayList<Article>,
    val sortBy: String,
    val source: String,
    val status: String
) : Serializable