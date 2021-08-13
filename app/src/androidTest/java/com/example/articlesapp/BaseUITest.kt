package com.example.articlesapp

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.articlesapp.article.di.idlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {
    protected val mockServer = MockWebServer()

    @Before
    @Throws(IOException::class, InterruptedException::class)
    fun setup() {
        mockServer.start(8080)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}