package com.example.articlesapp.article.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.databinding.ItemArticleBinding
import com.example.articlesapp.tools.ImageLoader

class ArticleRecyclerViewAdapter(
    private val articles: List<Article>,
    private val imageLoader: ImageLoader,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {

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
        holder.apply {
            title.text = article.title
            subtitle.text = article.subtitle
            imageLoader.load(itemView.context, article.image, image)
            root.setOnClickListener {
                listener(article.id)
            }
        }
    }

    override fun getItemCount(): Int = articles.size

    inner class ViewHolder(binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.articleTitle
        val subtitle: TextView = binding.articleSubtitle
        val image: ImageView = binding.articleImage
        val root: ConstraintLayout = binding.root
    }

}