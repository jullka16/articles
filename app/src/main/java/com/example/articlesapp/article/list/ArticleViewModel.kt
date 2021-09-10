package com.example.articlesapp.article.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val repository: ArticleRepository) :
    ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val articles = liveData<Result<List<Article>>> {
        loader.postValue(true)

        emitSource(repository.getArticles()
            .onEach {
                loader.postValue(false)
            }
            .asLiveData())
    }

}