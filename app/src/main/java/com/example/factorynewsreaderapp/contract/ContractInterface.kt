package com.example.factorynewsreaderapp.contract

import com.example.factorynewsreaderapp.data.Article

interface ContractInterface {

    interface Model {

        interface OnFinishedListener {

            fun onFinished(articlesList: ArrayList<Article>)
            fun onFailure(throwable: Throwable)
        }

        suspend fun getArticlesList(onFinishedListener: OnFinishedListener)
        suspend fun getRoomArticlesList(): ArrayList<Article>
        suspend fun clearRoomArticlesList()
        fun dataModifiedTime(): Long?
    }


    interface View {

        interface ListFragmentInterface {
            fun updateUI()
        }
        fun callFragment()
        fun showProgress()
        fun hideProgress()
        fun setSingleViewFragment(itemPosition: Int, articlesList: ArrayList<Article>)
        fun onResponseFailure(throwable: Throwable)
    }

    interface ItemView {
        fun setData(title: String, imageUrl: String)
    }

    interface Presenter {

        suspend fun requestDataFromServer()
        suspend fun requestDataFromRoom()
        fun onDestroy()
        fun onItemInteraction(position: Int)
        fun bind(itemView: ItemView, position: Int)
        fun getArticlesCount(): Int
    }
}