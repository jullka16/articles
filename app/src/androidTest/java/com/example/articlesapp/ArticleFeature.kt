package com.example.articlesapp

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.articlesapp.article.view.ArticleFragment
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ArticleFeature : BaseUITest() {
    val activityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed(R.string.articles)
    }

    @Test
    fun displayListOfArticles() {
        mockServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody("[{\"title\":\"Title 1\"  },  {    \"title\": \"Title2\"  },  {    \"title\": \"Title 3\"  },  {    \"title\": \"Title4\"  }  ]")
        )
        //Workaround for not getting the mocked response on time
        routeToFragment()
        assertRecyclerViewItemCount(R.id.articlesList, 4)

        onView(
            allOf(
                withId(R.id.articleTitle),
                isDescendantOfA(nthChildOf(withId(R.id.articlesList), 0))
            )
        ).check(matches(withText("Title 1")))
            .check(matches(isDisplayed()))
    }

    private fun routeToFragment() = activityRule
        .activity
        .supportFragmentManager
        .beginTransaction()
        .replace(R.id.mainContainer, ArticleFragment.newInstance())
        .commit()

    private fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}