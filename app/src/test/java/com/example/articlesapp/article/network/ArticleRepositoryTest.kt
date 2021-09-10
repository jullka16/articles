package com.example.articlesapp.article.network

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
    private val article: Article = mock()
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

    @Test
    fun testGetArticleDetails() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getArticleDetails(ARTICLE_ID)

        verify(service, times(1)).fetchArticleById(ARTICLE_ID)
    }

    @Test
    fun testIfArticleByIdIsEmittedFromRepository() = runBlockingTest {
        val repository = mockSuccessfulCase()

        assertEquals(article, repository.getArticleDetails(ARTICLE_ID).first().getOrNull())
    }

    @Test
    fun testIfErrorIsEmittedFromServiceWhenFetchingArticleById() = runBlockingTest {
        val repository = mockErrorCase()
        repository.getArticleDetails(ARTICLE_ID)

        assertEquals(exception, repository.getArticleDetails(ARTICLE_ID).first().exceptionOrNull())
    }


    private suspend fun mockSuccessfulCase(): ArticleRepository {
        whenever(service.fetchArticles()).thenReturn(
            flow {
                emit(Result.success(articles))
            }
        )
        whenever(service.fetchArticleById(ARTICLE_ID)).thenReturn(
            flow {
                emit(Result.success(article))
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
        whenever(service.fetchArticleById(ARTICLE_ID)).thenReturn(
            flow {
                emit(Result.failure<Article>(exception))
            }
        )
        return ArticleRepository(service)
    }

    companion object {
        private const val ERROR_MESSAGE = "500 internal error"
        private const val ARTICLE_ID = "1"
    }
}