package com.example.articlesapp.article.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.articlesapp.article.network.ArticleRepository
import javax.inject.Inject

class ArticleDetailsViewModelFactory @Inject constructor(private val repository: ArticleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleDetailsViewModel(repository) as T
    }
}