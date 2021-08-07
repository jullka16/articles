package com.example.articlesapp.article.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.databinding.ItemArticleBinding


class MyArticleRecyclerViewAdapter(
    private val articles: List<Article>
) : RecyclerView.Adapter<MyArticleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
    }

    override fun getItemCount(): Int = articles.size

    inner class ViewHolder(binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.articleTitle
    }

}