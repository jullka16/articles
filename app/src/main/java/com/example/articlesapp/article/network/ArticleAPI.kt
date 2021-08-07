package com.example.articlesapp.article.network

import retrofit2.http.GET

interface ArticleAPI {
    @GET("articles")
    suspend fun fetchAllArticles(): List<Article>
}
