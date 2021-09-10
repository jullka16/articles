package com.example.articlesapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDetailsFeature : BaseUITest() {

    @Test
    fun displaysArticleTitleAndDetails() {
        navigateToArticleDetails(0)
        assertDisplayed("Title 1")
        assertDisplayed("Body 1")
    }

    private fun navigateToArticleDetails(position: Int) {
        onView(
            CoreMatchers.allOf(
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.articlesList),
                        position
                    )
                )
            )
        ).perform(ViewActions.click())
    }

}