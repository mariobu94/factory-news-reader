package com.example.factorynewsreaderapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.factorynewsreaderapp.data.Article
import com.example.factorynewsreaderapp.databinding.ItemDetailsBinding
import com.squareup.picasso.Picasso

/**
 * Adapter for the Viewpager2.
 */

class ArticleDetailsAdapter(private val articleList: List<Article>) :
    RecyclerView.Adapter<ArticleDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.articleTitle.text = articleList[position].title
        holder.articleDescription.text = articleList[position].description
        Picasso.get().load(articleList[position].urlToImage).into(holder.articleImage)
    }

    override fun getItemCount(): Int {

        return articleList.size
    }

    class ViewHolder(binding: ItemDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        val articleTitle = binding.articleTitle
        val articleDescription = binding.articleDescription
        val articleImage = binding.articleImage
    }
}