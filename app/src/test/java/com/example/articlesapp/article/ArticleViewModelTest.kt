package com.example.articlesapp.article

import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import com.example.articlesapp.article.view.ArticleViewModel
import com.example.articlesapp.utils.BaseUnitTest
import com.example.articlesapp.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ArticleViewModelTest : BaseUnitTest() {

    private val repository: ArticleRepository = mock()
    private val articles: List<Article> = mock()
    private val expected = Result.success(articles)
    private val exception = RuntimeException(ERROR_MESSAGE)
    private val error = Result.failure<List<Article>>(exception)

    @Test
    fun testGetPlaylistFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()
        viewModel.articles.getValueForTest()

        verify(repository, times(1)).getArticles()
    }

    @Test
    fun testIfArticlesAreEmittedFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.articles.getValueForTest())
    }

    @Test
    fun testIfErrorEmittedWhenRepositoryReturnsError() = runBlockingTest {
        val viewModel = mockErrorCase()

        assertEquals(exception, viewModel.articles.getValueForTest()?.exceptionOrNull())
    }


    private suspend fun mockSuccessfulCase(): ArticleViewModel {
        runBlocking {
            whenever(repository.getArticles()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return ArticleViewModel(repository)
    }

    private suspend fun mockErrorCase(): ArticleViewModel {
        runBlocking {
            whenever(repository.getArticles()).thenReturn(
                flow {
                    emit(error)
                }
            )
        }
        return ArticleViewModel(repository)
    }

    companion object {
        private const val ERROR_MESSAGE = "500 internal error"
    }
}