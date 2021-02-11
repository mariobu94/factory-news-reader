package com.example.factorynewsreaderapp.data

import android.content.Context

object Injection {

    private lateinit var context: Context

    fun setUp(mContext: Context) {
        context = mContext
    }

    fun provideArticlesInteractor(): ArticlesInteractor {
        return ArticlesInteractor.getInstance(context)
    }
}