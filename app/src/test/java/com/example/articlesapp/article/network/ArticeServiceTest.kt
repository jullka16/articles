package com.example.articlesapp.article.network

import com.example.articlesapp.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ArticeServiceTest : BaseUnitTest() {

    private val api: ArticleAPI = mock()
    private val articles: List<Article> = mock()
    private val expected = Result.success(articles)
    private val exception = RuntimeException(ERROR_MESSAGE)
    private val error = Result.failure<List<Article>>(exception)
    private val article: Article = mock()
    private val expectedArticle = Result.success(article)

    @Test
    fun testFetchAllArticlesFromAPI() = runBlockingTest {
        val service = ArticleService(api)
        service.fetchArticles().first()

        verify(api, times(1)).fetchAllArticles()
    }

    @Test
    fun testIfValuesConvertedToResultAndEmitted() = runBlockingTest {
        val service = mockSuccessfulCase()

        assertEquals(expected, service.fetchArticles().first())
    }

    @Test
    fun testIfEmitsErrorWhenNetworkFails() = runBlockingTest {
        val service = mockFailureCase()

        assertEquals(error, service.fetchArticles().first())
    }

    @Test
    fun testFetchArticleByIdFromAPI() = runBlockingTest {
        val service = ArticleService(api)
        service.fetchArticleById(ARTICLE_ID).first()

        verify(api, times(1)).fetchArticleById(ARTICLE_ID)
    }

    @Test
    fun testIfValueOfArticleConvertedToResultAndEmitted() = runBlockingTest {
        val service = mockSuccessfulCase()

        assertEquals(expectedArticle, service.fetchArticleById(ARTICLE_ID).first())
    }

    @Test
    fun testIfArticleEmitsErrorWhenNetworkFails() = runBlockingTest {
        val service = mockFailureCase()

        assertEquals(error, service.fetchArticleById(ARTICLE_ID).first())
    }


    private suspend fun mockSuccessfulCase(): ArticleService {
        whenever(api.fetchAllArticles()).thenReturn(articles)
        whenever(api.fetchArticleById(ARTICLE_ID)).thenReturn(article)
        return ArticleService(api)
    }

    private suspend fun mockFailureCase(): ArticleService {
        whenever(api.fetchAllArticles()).thenThrow(exception)
        whenever(api.fetchArticleById(ARTICLE_ID)).thenThrow(exception)
        return ArticleService(api)
    }

    companion object {
        private const val ERROR_MESSAGE = "500 internal error"
        private const val ARTICLE_ID = "1"
    }
}