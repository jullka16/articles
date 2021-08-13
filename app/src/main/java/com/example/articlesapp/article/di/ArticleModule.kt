package com.example.articlesapp.article.di

import com.example.articlesapp.BuildConfig
import com.example.articlesapp.article.network.ArticleAPI
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client =
    OkHttpClient.Builder().addInterceptor(interceptor).build()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)

@Module
@InstallIn(FragmentComponent::class)
class ArticleModule {
    @Provides
    fun articlesAPI(retrofit: Retrofit) = retrofit.create(ArticleAPI::class.java)

    @Provides
    fun retrofit() = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}