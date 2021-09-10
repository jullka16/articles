package com.example.articlesapp.article.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleDetailsViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {
    val loader = MutableLiveData<Boolean>()
    val articleDetails = MutableLiveData<Result<Article>>()

    fun getArticleDetails(id: String) {
        loader.postValue(true)
        viewModelScope.launch {
            repository.getArticleDetails(id)
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    articleDetails.postValue(it)
                }
        }
    }

}