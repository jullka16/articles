package com.example.articlesapp.article.network

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleRepository @Inject constructor(private val service: ArticleService) {
    suspend fun getArticles(): Flow<Result<List<Article>>> = service.fetchArticles()
}
