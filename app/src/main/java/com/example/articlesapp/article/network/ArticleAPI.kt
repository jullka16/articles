package com.example.articlesapp.article.network

import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleAPI {
    @GET("articles")
    suspend fun fetchAllArticles(): List<Article>

    @GET("articles/{id}")
    suspend fun fetchArticleById(@Path("id") id: String): Article
}
