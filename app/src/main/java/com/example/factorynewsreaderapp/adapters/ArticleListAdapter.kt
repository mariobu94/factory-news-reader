package com.example.factorynewsreaderapp.adapters

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.factorynewsreaderapp.contract.ContractInterface
import com.example.factorynewsreaderapp.databinding.ItemListBinding
import com.example.factorynewsreaderapp.presenter.ArticlesPresenter
import com.squareup.picasso.Picasso

/**
 * Adapter for the [RecyclerView].
 */

class ArticleListAdapter(private val articlesPresenter: ArticlesPresenter) :
    RecyclerView.Adapter<ArticleListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, articlesPresenter)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        articlesPresenter.bind(holder, position)
    }

    override fun getItemCount(): Int {

        return articlesPresenter.getArticlesCount()
    }

    class MyViewHolder(private val binding: ItemListBinding, private val presenter: ArticlesPresenter) :
        RecyclerView.ViewHolder(binding.root),
        ContractInterface.ItemView, View.OnClickListener {


        override fun setData(title: String, imageUrl: String) {

            val myShader: Shader = LinearGradient(
                0.0f, 250.0f, 0.0f, 0.0f,
                Color.WHITE, Color.BLACK,
                Shader.TileMode.CLAMP
            )

            with(binding) {
                articleTitle.text = title
                articleTitle.paint.shader = myShader
                Picasso.get().load(imageUrl).into(articleImage)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {

            presenter.onItemInteraction(adapterPosition)
        }
    }
}