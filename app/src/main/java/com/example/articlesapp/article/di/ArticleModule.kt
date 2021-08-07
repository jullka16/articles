package com.example.articlesapp.article.di

import com.example.articlesapp.article.network.ArticleAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(FragmentComponent::class)
class ArticleModule {
    @Provides
    fun articlesAPI(retrofit: Retrofit) = retrofit.create(ArticleAPI::class.java)

    @Provides
    fun interceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun client(interceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    fun retrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl("http://192.168.1.23:3000/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}