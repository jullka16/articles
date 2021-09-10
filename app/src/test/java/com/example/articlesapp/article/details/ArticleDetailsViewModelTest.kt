package com.example.articlesapp.article.details

import com.example.articlesapp.article.network.Article
import com.example.articlesapp.article.network.ArticleRepository
import com.example.articlesapp.utils.BaseUnitTest
import com.example.articlesapp.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class ArticleDetailsViewModelTest : BaseUnitTest() {

    private val repository: ArticleRepository = mock()
    private val id = "1"
    private val articleDetails: Article = mock()
    private val expected = Result.success(articleDetails)
    private val exception = RuntimeException("My Error")
    private val expectedError = Result.failure<Article>(exception)
    private lateinit var viewModel: ArticleDetailsViewModel

    @Test
    fun testGetArticleDetailsFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getArticleDetails(id)

        viewModel.articleDetails.getValueForTest()

        verify(repository, times(1)).getArticleDetails(id)
    }

    @Test
    fun testIfArticleDetailsAreEmittedFromRepository() = runBlockingTest {
        mockSuccessfulCase()
        viewModel.getArticleDetails(id)

        assertEquals(expected, viewModel.articleDetails.getValueForTest())
    }

    @Test
    fun testIfErrorEmittedWhenRepositoryReturnsError() = runBlockingTest {
        mockErrorCase()
        viewModel.getArticleDetails(id)

        assertEquals(expectedError, viewModel.articleDetails.getValueForTest())
    }

    private suspend fun mockSuccessfulCase() {
        whenever(repository.getArticleDetails(id)).thenReturn(flow {
            emit(expected)
        })
        viewModel = ArticleDetailsViewModel(repository)
    }

    private suspend fun mockErrorCase() {
        whenever(repository.getArticleDetails(id)).thenReturn(flow {
            emit(expectedError)
        })
        viewModel = ArticleDetailsViewModel(repository)
    }
}