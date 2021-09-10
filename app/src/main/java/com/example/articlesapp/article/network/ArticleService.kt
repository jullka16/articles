package com.example.articlesapp.article.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticleService @Inject constructor(private val api: ArticleAPI) {
    suspend fun fetchArticles(): Flow<Result<List<Article>>> {
        return flow {
            emit(Result.success(api.fetchAllArticles()))
        }.catch {
            emit(Result.failure(it))
        }
    }

    suspend fun fetchArticleById(id: String): Flow<Result<Article>> {
        return flow {
            emit(Result.success(api.fetchArticleById(id)))
        }.catch {
            emit(Result.failure(it))
        }
    }

}
