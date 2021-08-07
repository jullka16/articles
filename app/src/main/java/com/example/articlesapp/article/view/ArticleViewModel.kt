package com.example.articlesapp.article.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {

    val articles = liveData<Result<List<Article>>> {
        emitSource(repository.getArticles().asLiveData())
    }

}