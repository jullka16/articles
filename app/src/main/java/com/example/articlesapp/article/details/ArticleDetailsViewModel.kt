package com.example.articlesapp.article.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {
    val articleDetails = MutableLiveData<Result<Article>>()

    fun getArticleDetails(id: String) {
        viewModelScope.launch {
            repository.getArticleDetails(id).collect {
                articleDetails.postValue(it)
            }
        }
    }

}