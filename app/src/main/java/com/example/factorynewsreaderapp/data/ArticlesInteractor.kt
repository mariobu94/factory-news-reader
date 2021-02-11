package com.example.factorynewsreaderapp.data

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.factorynewsreaderapp.contract.ContractInterface
import com.example.factorynewsreaderapp.data.room.DatabaseClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val URL_PATH = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=6946d0c07a1c4555a4186bfcade76398"

class ArticlesInteractor(context: Context) : ContractInterface.Model {

    private val requestQueue = Volley.newRequestQueue(context)
    private val localArticles = DatabaseClient.getInstance(context)?.getArticlesDatabase()?.articlesDao()
    private var articlesList: ArrayList<Article> = ArrayList()

    /**
     * Used for retrieving data from the network and storing data into the Room.
     */
    override suspend fun getArticlesList(onFinishedListener: ContractInterface.Model.OnFinishedListener) {

        val jsonObjectRequest = object : JsonObjectRequest(Method.GET, URL_PATH, null,
            Response.Listener {

                val articlesJsonString = it.getString("articles")
                articlesList = Gson().fromJson(articlesJsonString, object : TypeToken<ArrayList<Article>>() {}.type)
                articlesList.forEach { Article -> Article.dataModified = System.currentTimeMillis() }

                GlobalScope.launch(Dispatchers.IO) {

                    localArticles?.clearAllArticles()
                    localArticles?.insertAllArticles(articlesList)
                }

                onFinishedListener.onFinished(articlesList)

            },
            { error ->
                onFinishedListener.onFailure(error)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    /**
     * Used for retrieving data from the Room.
     */
    override suspend fun getRoomArticlesList(): ArrayList<Article> {

        val articlesList = localArticles?.getAllArticles()
        val articlesArrayList = arrayListOf<Article>()
        articlesArrayList.addAll(articlesList as Collection<Article>)
        return articlesArrayList
    }

    override suspend fun clearRoomArticlesList() {
        localArticles?.clearAllArticles()
    }

    override fun dataModifiedTime(): Long? {
        return localArticles?.dataModifiedTime()
    }

    companion object {

        private var INSTANCE: ArticlesInteractor? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(context: Context): ArticlesInteractor {
            return INSTANCE ?: ArticlesInteractor(context)
                .apply { INSTANCE = this }
        }
    }
}