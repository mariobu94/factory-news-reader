package com.example.factorynewsreaderapp.presenter

import com.example.factorynewsreaderapp.contract.ContractInterface
import com.example.factorynewsreaderapp.data.Article
import com.example.factorynewsreaderapp.data.ArticlesInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticlesPresenter(
    private var articleListView: ContractInterface.View?,
    articlesInteractor: ArticlesInteractor

) : ContractInterface.Presenter, ContractInterface.Model.OnFinishedListener {

    private var articleListModel: ContractInterface.Model = articlesInteractor
    private var articlesList = ArrayList<Article>()

    /**
     * Compares the current time with the last time when the data was downloaded.
     * If the data is older than 5 minutes it will be downloaded again, otherwise it will be retrieved from the Room.
     */
    fun requestType() {
        val timeOfLastData = articleListModel.dataModifiedTime()!!
        val currentTime = System.currentTimeMillis()

        GlobalScope.launch(Dispatchers.IO) {
            if (currentTime - timeOfLastData <= 300000) {
                requestDataFromRoom()
            } else {
                requestDataFromServer()
            }
        }
    }

    override suspend fun requestDataFromServer() {

        GlobalScope.launch(Dispatchers.Main) {
            articleListView?.showProgress()
        }
        articleListModel.getArticlesList(this)
    }

    override suspend fun requestDataFromRoom() {

        articlesList = articleListModel.getRoomArticlesList()
    }

    override fun onDestroy() {

        articleListView = null
    }

    override fun onItemInteraction(position: Int) {

        articleListView?.setSingleViewFragment(position, articlesList)
    }

    override fun onFinished(articlesList: ArrayList<Article>) {

        this.articlesList = articlesList
        articleListView?.hideProgress()
        articleListView?.callFragment()
    }

    override fun onFailure(throwable: Throwable) {

        articleListView?.onResponseFailure(throwable)
        articleListView?.hideProgress()
    }

    override fun bind(itemView: ContractInterface.ItemView, position: Int) {

        val itemPosition = articlesList[position]
        itemView.setData(itemPosition.title, itemPosition.urlToImage)
    }

    override fun getArticlesCount(): Int {

        return articlesList.size
    }

}

