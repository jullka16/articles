package com.example.articlesapp.article

import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import com.example.articlesapp.article.network.ArticleService
import com.example.articlesapp.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ArticleRepositoryTest : BaseUnitTest() {

    private val service: ArticleService = mock()
    private val articles: List<Article> = mock()
    private val exception = RuntimeException(ERROR_MESSAGE)

    @Test
    fun testGetArticlesFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getArticles()

        verify(service, times(1)).fetchArticles()
    }

    @Test
    fun testIfArticlesAreEmittedFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()

        assertEquals(articles, repository.getArticles().first().getOrNull())
    }

    @Test
    fun testIfErrorIsEmittedFromService() = runBlockingTest {
        val repository = mockErrorCase()

        assertEquals(exception, repository.getArticles().first().exceptionOrNull())
    }


    private suspend fun mockSuccessfulCase(): ArticleRepository {
        whenever(service.fetchArticles()).thenReturn(
            flow {
                emit(Result.success(articles))
            }
        )
        return ArticleRepository(service)
    }

    private suspend fun mockErrorCase(): ArticleRepository {
        whenever(service.fetchArticles()).thenReturn(
            flow {
                emit(Result.failure<List<Article>>(exception))
            }
        )
        return ArticleRepository(service)
    }

    companion object {
        private const val ERROR_MESSAGE = "500 internal error"
    }
}