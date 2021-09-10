package com.example.articlesapp.article.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.articlesapp.article.network.ArticleRepository
import javax.inject.Inject

class ArticleViewModelFactory @Inject constructor(private val repository: ArticleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(repository) as T
    }

}
