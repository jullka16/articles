package com.example.articlesapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.articlesapp.article.di.idlingResource
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ArticleFeature : BaseUITest() {

    @Test
    fun displayScreenTitle() {
        assertDisplayed(R.string.articles)
    }

    @Test
    fun displayListOfArticles() {
        assertRecyclerViewItemCount(R.id.articlesList, 4)

        onView(
            allOf(
                withId(R.id.articleTitle),
                isDescendantOfA(nthChildOf(withId(R.id.articlesList), 0))
            )
        ).check(matches(withText("Title 1")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingArticles() {
        IdlingRegistry.getInstance().unregister(idlingResource)
        assertDisplayed(R.id.articleLoader)
    }

    @Test
    fun hidesLoaderWhenArticlesFetched() {
        assertNotDisplayed(R.id.articleLoader)
    }
}