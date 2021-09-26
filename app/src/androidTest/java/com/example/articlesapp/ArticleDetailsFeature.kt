package com.example.articlesapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.articlesapp.article.di.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleDetailsFeature : BaseUITest() {

    @Test
    fun displaysArticleTitleAndDetails() {
        navigateToArticleDetails(0)

        assertDisplayed("Newcastle goalkeeper Karl Darlow urges players to get vaccinated for Covid-19")
        assertDisplayed("Darlow's warning comes as the Premier League prepares to send a video to clubs next week in which the government's deputy chief medical officer Professor Jonathan Van-Tam urges players to ignore myths and misinformation about the vaccine. There is growing concern about the numbers refusing get inoculated, with almost a third in the English Football League still not immunised.")
    }

    @Test
    fun displayArticleImage() {
        navigateToArticleDetails(0)

        onView(ViewMatchers.withId(R.id.articleImage)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun displaysProgressBarWhileFetchingArticleDetails() {
        IdlingRegistry.getInstance().unregister(idlingResource)

        Thread.sleep(2000)
        navigateToArticleDetails(0)

        assertDisplayed(R.id.articleDetailsLoader)
    }

    @Test
    fun hideLoader() {
        navigateToArticleDetails(0)

        assertNotDisplayed(R.id.articleDetailsLoader)
    }

    private fun navigateToArticleDetails(position: Int) {
        onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.articleTitle),
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